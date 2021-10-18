package com.bjsxt.service;

import com.bjsxt.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPrivilegeService extends IService<SysPrivilege>{


    /**
     * 获取该角色在该菜单下的权限情况
     * @param id
     * @param roleId
     * @return
     */
    List<SysPrivilege> getAllSysPrivilege(Long id, Long roleId);
}
