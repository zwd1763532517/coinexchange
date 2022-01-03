package com.bjsxt.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Account;
import com.bjsxt.domain.Config;
import com.bjsxt.dto.UserDto;
import com.bjsxt.feign.UserBankServiceFeign;
import com.bjsxt.feign.UserServiceFeign;
import com.bjsxt.model.dto.UserBankDto;
import com.bjsxt.model.param.CashSellParam;
import com.bjsxt.service.AccountService;
import com.bjsxt.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.CashWithdrawals;
import com.bjsxt.mapper.CashWithdrawalsMapper;
import com.bjsxt.service.CashWithdrawalsService;
import org.springframework.util.CollectionUtils;

@Service
public class CashWithdrawalsServiceImpl extends ServiceImpl<CashWithdrawalsMapper, CashWithdrawals> implements CashWithdrawalsService {


    @Autowired
    UserServiceFeign userServiceFeign;

    @Autowired
    AccountService accountService;

    @Autowired
    ConfigService configService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserBankServiceFeign userBankServiceFeign;

    @Override
    public Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status) {
        return page(page, new LambdaQueryWrapper<CashWithdrawals>()
                .eq(CashWithdrawals::getUserId, userId)
                .eq(status != null, CashWithdrawals::getStatus, status));
    }

    @Override
    public boolean sell(Long userId, CashSellParam cashSellParam) {

        checkCashSellParam(cashSellParam);

        Map<Long, UserDto> basicUsers = userServiceFeign.getBasicUsers(Arrays.asList(userId), null, null);

        if (CollectionUtils.isEmpty(basicUsers)) {
            throw new IllegalArgumentException("用户的id错误");
        }
        UserDto userDto = basicUsers.get(userId);
        // 2 手机验证码
        validatePhoneCode(userDto.getMobile(), cashSellParam.getValidateCode());

        // 3 支付密码
        checkUserPayPassword(userDto.getPaypassword(), cashSellParam.getPayPassword());

        // 4 远程调用查询用户的银行卡
        UserBankDto userBankInfo = userBankServiceFeign.getUserBankInfo(userId);
        if (userBankInfo == null) {
            throw new IllegalArgumentException("该用户暂未绑定银行卡信息");
        }
        String remark = RandomUtil.randomNumbers(6);
        // 5 通过数量得到本次交易的金额
        BigDecimal amount = getCashWithdrawalsAmount(cashSellParam.getNum());

        // 6 计算本次的手续费
        BigDecimal fee = getCashWithdrawalsFee(amount);

        // 7 查询用户的账号ID
        Account account = accountService.findByUserAndCoin(userId, "GCN");

        // 8 订单的创建
        CashWithdrawals cashWithdrawals = new CashWithdrawals();

        cashWithdrawals.setUserId(userId);
        cashWithdrawals.setAccountId(account.getId());
        cashWithdrawals.setCoinId(cashSellParam.getCoinId());
        cashWithdrawals.setStatus((byte) 0);
        cashWithdrawals.setStep((byte) 1);
        cashWithdrawals.setNum(cashSellParam.getNum());
        cashWithdrawals.setMum(amount.subtract(fee)); // 实际金额 = amount-fee
        cashWithdrawals.setFee(fee);
        cashWithdrawals.setBank(userBankInfo.getBank());
        cashWithdrawals.setBankCard(userBankInfo.getBankCard());
        cashWithdrawals.setBankAddr(userBankInfo.getBankAddr());
        cashWithdrawals.setBankProv(userBankInfo.getBankProv());
        cashWithdrawals.setBankCity(userBankInfo.getBankCity());
        cashWithdrawals.setTruename(userBankInfo.getRealName());
        cashWithdrawals.setRemark(remark);
        boolean save = save(cashWithdrawals);
        if (save) { //
            // 扣减总资产--account-->accountDetail
            accountService.lockUserAmount(userId, cashWithdrawals.getCoinId(), cashWithdrawals.getMum(), "withdrawals_out", cashWithdrawals.getId(), cashWithdrawals.getFee());
        }
        return save;
    }

    /**
     * 1 手机验证码
     * 2 支付密码
     * 3 提现相关的验证
     * @param cashSellParam
     */
    private void checkCashSellParam(CashSellParam cashSellParam) {
        // 1 提现状态
        Config cashWithdrawalsStatus = configService.getConfigByCode("WITHDRAW_STATUS");
        if (Integer.valueOf(cashWithdrawalsStatus.getValue()) != 1) {
            throw new IllegalArgumentException("提现暂未开启");
        }
        // 2 提现的金额
        @NotNull BigDecimal cashSellParamNum = cashSellParam.getNum();
        // 2.1 最小的提现额度100
        Config cashWithdrawalsConfigMin = configService.getConfigByCode("WITHDRAW_MIN_AMOUNT");
        if (cashSellParamNum.compareTo(new BigDecimal(cashWithdrawalsConfigMin.getValue())) < 0) {
            throw new IllegalArgumentException("检查提现的金额");
        }
        // 2.1 最大的提现额度200
        // 201
        Config cashWithdrawalsConfigMax = configService.getConfigByCode("WITHDRAW_MAX_AMOUNT");
        if (cashSellParamNum.compareTo(new BigDecimal(cashWithdrawalsConfigMax.getValue())) >= 0) {
            throw new IllegalArgumentException("检查提现的金额");
        }
    }

    /**
     * 校验用户的手机验证码
     * @param mobile
     * @param validateCode
     */
    private void validatePhoneCode(String mobile, String validateCode) {
        // 验证:SMS:CASH_WITHDRAWS:mobile
        String code = (String) redisTemplate.opsForValue().get("SMS:CASH_WITHDRAWS:" + mobile);
        if (!validateCode.equals(code)) {
            throw new IllegalArgumentException("验证码错误");
        }
    }


    /**
     * 支付密码的校验
     * @param payDBPassword
     * @param payPassword
     */
    private void checkUserPayPassword(String payDBPassword,String payPassword){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(payPassword, payDBPassword);
        if (!matches) {
            throw new IllegalArgumentException("支付密码错误");
        }
    }

    /**
     * 通过数量计算金额
     * @param num
     * @return
     */
    private BigDecimal getCashWithdrawalsAmount(BigDecimal num) {
        Config rateConfig = configService.getConfigByCode("USDT2CNY");
        return num.multiply(new BigDecimal(rateConfig.getValue())).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 计算手续费
     *
     * @param amount
     * @return
     */
    private BigDecimal getCashWithdrawalsFee(BigDecimal amount) {
        // 1 通过总金额* 费率 = 手续费
        // 2 若金额较小---->最小的提现的手续费
        // 最小的提现费用
        Config withdrawMinPoundage = configService.getConfigByCode("WITHDRAW_MIN_POUNDAGE");
        BigDecimal withdrawMinPoundageFee = new BigDecimal(withdrawMinPoundage.getValue());
        // 提现的费率
        Config withdrawPoundageRate = configService.getConfigByCode("WITHDRAW_POUNDAGE_RATE");
        // 通过费率计算的手续费
        BigDecimal poundageFee = amount.multiply(new BigDecimal(withdrawPoundageRate.getValue())).setScale(2, RoundingMode.HALF_UP);
        return poundageFee.min(withdrawMinPoundageFee).equals(poundageFee) ? withdrawMinPoundageFee : poundageFee;
    }
}
