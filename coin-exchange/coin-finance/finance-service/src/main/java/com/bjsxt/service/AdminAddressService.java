package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.AdminAddress;
import com.baomidou.mybatisplus.extension.service.IService;
public interface AdminAddressService extends IService<AdminAddress>{


    /**
     *
     * @param page
     * @param coinId
     * @return
     */
    Page<AdminAddress> findByPage(Page<AdminAddress> page, Long coinId);
}
