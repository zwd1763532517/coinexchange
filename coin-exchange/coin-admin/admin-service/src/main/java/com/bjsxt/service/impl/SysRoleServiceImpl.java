package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.SysRole;
import com.bjsxt.mapper.SysRoleMapper;
import com.bjsxt.service.SysRoleService;
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public boolean isSuperAdmin(Long userId) {
        String roleCode = sysRoleMapper.getUserRoleCode(userId);
        if(!StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN")){
            return true;
        }
        return false;
    }

    @Override
    public Page<SysRole> findByPage(Page<SysRole> page, String name) {
        return this.page(page,new LambdaQueryWrapper<SysRole>().like(
                !StringUtils.isEmpty(name),
                SysRole::getName,
                name
        ));
    }
}
