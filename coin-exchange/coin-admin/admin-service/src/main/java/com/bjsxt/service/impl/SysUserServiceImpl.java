package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysUserRole;
import com.bjsxt.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.SysUserMapper;
import com.bjsxt.domain.SysUser;
import com.bjsxt.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

    @Autowired
    private SysUserRoleService sysUserRoleService;
    
    @Override
    public Page<SysUser> findUsersByPage(Page<SysUser> page, String mobile, String fullname) {
        Page<SysUser> userInfos = this.page(page, new LambdaQueryWrapper<SysUser>().like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
                .like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname)
        );
        List<SysUser> records = userInfos.getRecords();
        if(!CollectionUtils.isEmpty(records)){
            for (SysUser record : records) {
                List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId()));
                if(!CollectionUtils.isEmpty(userRoles)){
                    record.setRole_strings(userRoles.stream().map(sysUserRole -> sysUserRole.getRoleId().toString()).collect(Collectors.joining(",")));
                }
            }
        }
        return userInfos;
    }

    @Transactional
    @Override
    public boolean addUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(password);
        sysUser.setPassword(encode);
        String role_strings = sysUser.getRole_strings();
        boolean save = this.save(sysUser);
        if(save){
            if(!StringUtils.isEmpty(role_strings)){
                String[] roleIds = role_strings.split(",");
                ArrayList<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
                for (String roleId : roleIds) {
                    SysUserRole sysUserRole = new SysUserRole();
                    sysUserRole.setRoleId(Long.valueOf(roleId));
                    sysUserRole.setUserId(sysUser.getId());
                    sysUserRoleList.add(sysUserRole);
                }
                boolean b = sysUserRoleService.saveBatch(sysUserRoleList);
                return b;
            }
        }
        return save;
    }


    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        boolean b = super.removeByIds(idList);
        sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId,idList));
        return b;
    }
}
