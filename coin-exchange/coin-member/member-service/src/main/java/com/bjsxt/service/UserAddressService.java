package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;
public interface UserAddressService extends IService<UserAddress>{


    /**
     *
     * @param page
     * @param userId
     * @return
     */
    Page<UserAddress> findByPage(Page<UserAddress> page, Long userId);
}
