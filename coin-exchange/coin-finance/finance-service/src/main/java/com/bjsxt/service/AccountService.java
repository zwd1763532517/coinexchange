package com.bjsxt.service;

import com.bjsxt.domain.Account;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

public interface AccountService extends IService<Account>{


    /**
     * 查询某个用户的货币资产
     * @param userId
     * @param coinName
     * @return
     */
    Account findByUserAndCoin(Long userId, String coinName);

    /**
     * 暂时锁定用户的资产
     * @param userId
     * @param coinId
     * @param mum
     * @param withdrawals_out
     * @param id
     * @param fee
     */
    void lockUserAmount(Long userId, Long coinId, BigDecimal mum, String withdrawals_out, Long id, BigDecimal fee);
}
