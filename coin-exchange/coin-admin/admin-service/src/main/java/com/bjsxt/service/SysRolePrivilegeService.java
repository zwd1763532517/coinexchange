package com.bjsxt.service;

import com.bjsxt.domain.SysMenu;
import com.bjsxt.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.model.RolePrivilegesDto;
import com.bjsxt.response.R;

import java.util.List;

public interface SysRolePrivilegeService extends IService<SysRolePrivilege>{


    /**
     * 根据角色id获取其下的权限信息情况
     * @param roleId
     * @return
     */
    List<SysMenu> findSysMenuAndPrivileges(Long roleId);

    /**
     * 修改角色对应的权限
     * @param dto
     * @return
     */
    boolean grantPrivileges(RolePrivilegesDto dto);
}
