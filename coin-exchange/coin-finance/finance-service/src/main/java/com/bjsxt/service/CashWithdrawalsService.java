package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.CashWithdrawals;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CashWithdrawalsService extends IService<CashWithdrawals>{


    /**
     *
     * @param page
     * @param userId
     * @param status
     * @return
     */
    Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status);
}
