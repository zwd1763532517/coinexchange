package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserWallet;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserWalletService extends IService<UserWallet>{


    /**
     *
     * @param page
     * @param userId
     * @return
     */
    Page<UserWallet> findByPage(Page<UserWallet> page, Long userId);

    /**
     *
     * @return
     */
    List<UserWallet> findUserWallets(Long userId, Long coinId);

    /**
     * 删除用户的提现地址
     * @param addressId
     * @param payPassword
     * @return
     */
    boolean deleteUserWallet(Long addressId, String payPassword);
}
