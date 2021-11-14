package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjsxt.domain.Coin;
import com.bjsxt.domain.Config;
import com.bjsxt.service.CoinService;
import com.bjsxt.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.Account;
import com.bjsxt.mapper.AccountMapper;
import com.bjsxt.service.AccountService;
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService{

    @Autowired
    private CoinService coinService ;

    @Autowired
    private ConfigService configService ;

    @Override
    public Account findByUserAndCoin(Long userId, String coinName) {
        Coin coin  = coinService.getCoinByCoinName(coinName) ;
        if(coin==null){
            throw new IllegalArgumentException("货币不存在") ;
        }
        Account account = getOne(new LambdaQueryWrapper<Account>()
                .eq(Account::getUserId, userId)
                .eq(Account::getCoinId, coin.getId())
        );

        if(account==null){
            throw new IllegalArgumentException("该资产不存在") ;
        }

        Config sellRateConfig = configService.getConfigByCode("USDT2CNY");

        account.setSellRate(new BigDecimal(sellRateConfig.getValue())); // 出售的费率

        Config setBuyRateConfig = configService.getConfigByCode("CNY2USDT");
        account.setBuyRate(new BigDecimal(setBuyRateConfig.getValue())); // 买进来的费率
        return account ;
    }
}
