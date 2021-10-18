package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser>{


    /**
     *
     * @param page
     * @param mobile
     * @param fullname
     * @return
     */
    Page<SysUser> findUsersByPage(Page<SysUser> page, String mobile, String fullname);

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    boolean addUser(SysUser sysUser);
}
