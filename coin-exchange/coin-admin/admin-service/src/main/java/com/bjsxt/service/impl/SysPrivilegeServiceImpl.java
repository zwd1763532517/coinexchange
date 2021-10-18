package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.SysPrivilege;
import com.bjsxt.mapper.SysPrivilegeMapper;
import com.bjsxt.service.SysPrivilegeService;
import org.springframework.util.CollectionUtils;

@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService{

    @Autowired
    private SysPrivilegeMapper sysPrivilegeMapper;

    @Override
    public List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId) {
        //查询所有该菜单下的权限
        List<SysPrivilege> sysPrivileges = this.list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
        if(CollectionUtils.isEmpty(sysPrivileges)){
            return Collections.emptyList();
        }
        //当前角色具有的权限信息也要带着
        for (SysPrivilege sysPrivilege : sysPrivileges) {
            Set<Long> currentRoleSysPrivilegeIds = sysPrivilegeMapper.getPrivilegeByRoleId(roleId);
            if(currentRoleSysPrivilegeIds.contains(sysPrivilege.getId())){
                sysPrivilege.setOwn(1);
            }
        }
        return sysPrivileges;
    }
}
