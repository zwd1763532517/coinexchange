package com.bjsxt.service;

import com.bjsxt.domain.Account;
import com.baomidou.mybatisplus.extension.service.IService;
public interface AccountService extends IService<Account>{


    /**
     * 查询某个用户的货币资产
     * @param userId
     * @param coinName
     * @return
     */
    Account findByUserAndCoin(Long userId, String coinName);
}
