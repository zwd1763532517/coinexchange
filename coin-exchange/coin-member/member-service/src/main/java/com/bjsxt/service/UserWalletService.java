package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserWallet;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserWalletService extends IService<UserWallet>{


    /**
     *
     * @param page
     * @param userId
     * @return
     */
    Page<UserWallet> findByPage(Page<UserWallet> page, Long userId);
}
