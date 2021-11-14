package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.CashWithdrawals;
import com.bjsxt.mapper.CashWithdrawalsMapper;
import com.bjsxt.service.CashWithdrawalsService;
@Service
public class CashWithdrawalsServiceImpl extends ServiceImpl<CashWithdrawalsMapper, CashWithdrawals> implements CashWithdrawalsService{

    @Override
    public Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status) {
        return page(page,new LambdaQueryWrapper<CashWithdrawals>()
                .eq(CashWithdrawals::getUserId,userId)
                .eq(status!=null,CashWithdrawals::getStatus,status));
    }
}
