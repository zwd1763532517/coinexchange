package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SysRoleService extends IService<SysRole>{


    /**
     * 判断一个用户是否为超级管理员
     * @param userId
     * @return
     */
    boolean isSuperAdmin(Long userId);

    /**
     * 根据名字获取角色名称
     * @param page
     * @param name
     * @return
     */
    Page<SysRole> findByPage(Page<SysRole> page, String name);
}
