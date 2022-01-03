package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.CashWithdrawals;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.model.param.CashSellParam;

public interface CashWithdrawalsService extends IService<CashWithdrawals>{


    /**
     *
     * @param page
     * @param userId
     * @param status
     * @return
     */
    Page<CashWithdrawals> findCashWithdrawals(Page<CashWithdrawals> page, Long userId, Byte status);

    /**
     * GCN的卖出操作
     * @param userId
     * @param cashSellParam
     * @return
     */
    boolean sell(Long userId, CashSellParam cashSellParam);
}
