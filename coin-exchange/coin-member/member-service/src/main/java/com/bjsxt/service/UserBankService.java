package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserBank;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserBankService extends IService<UserBank>{


    /**
     * 根据会员id获取他的银行卡信息
     * @param page
     * @param userId
     * @return
     */
    Page<UserBank> findByPage(Page<UserBank> page, Long userId);

    /**
     *
     * @param userId
     * @return
     */
    UserBank getUserBankByUserId(Long userId);

    /**
     *
     * @param userId
     * @param userBank
     * @return
     */
    boolean bindBank(Long userId, UserBank userBank);
}
