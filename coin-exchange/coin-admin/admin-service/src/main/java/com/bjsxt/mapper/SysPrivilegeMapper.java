package com.bjsxt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjsxt.domain.SysPrivilege;

import java.util.Set;

public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
    /**
     * 根据角色获取其具有的权限
     * @param roleId
     * @return
     */
    Set<Long> getPrivilegeByRoleId(Long roleId);
}