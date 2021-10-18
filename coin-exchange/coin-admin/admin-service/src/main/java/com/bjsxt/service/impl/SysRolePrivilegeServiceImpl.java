package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bjsxt.domain.SysMenu;
import com.bjsxt.domain.SysPrivilege;
import com.bjsxt.model.RolePrivilegesDto;
import com.bjsxt.service.SysMenuService;
import com.bjsxt.service.SysPrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.SysRolePrivilegeMapper;
import com.bjsxt.domain.SysRolePrivilege;
import com.bjsxt.service.SysRolePrivilegeService;
import org.springframework.util.CollectionUtils;

@Service
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService{

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @Override
    public List<SysMenu> findSysMenuAndPrivileges(Long roleId) {
        //查询所有的菜单
        List<SysMenu> list = sysMenuService.list();
        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }
        List<SysMenu> rootMenus = list.stream().filter(sysMenu -> sysMenu.getParentId() == null).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(rootMenus)){
            return Collections.emptyList();
        }
        ArrayList<SysMenu> subMenus = new ArrayList<>();
        for (SysMenu rootMenu : rootMenus) {
            subMenus.addAll(getChildMenus(rootMenu.getId(),roleId,list));
        }
        return subMenus;
    }

    @Override
    public boolean grantPrivileges(RolePrivilegesDto dto) {
        Long roleId = dto.getRoleId();
        this.remove(new LambdaQueryWrapper<SysRolePrivilege>().eq(SysRolePrivilege::getRoleId, roleId));
        List<Long> privileges = dto.getPrivilegeIds();
        if(!CollectionUtils.isEmpty(privileges)){
            List<SysRolePrivilege> sysRolePrivileges = new ArrayList<>();
            for (Long privilege : privileges) {
                SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
                sysRolePrivilege.setRoleId(dto.getRoleId());
                sysRolePrivilege.setPrivilegeId(privilege);
                sysRolePrivileges.add(sysRolePrivilege);
            }
            boolean b = this.saveBatch(sysRolePrivileges);
            return b;
        }
        return true;
    }

    /**
     * 获取所有一级菜单下的各个子菜单及其权限
     * @param id
     * @param roleId
     * @param list
     * @return
     */
    private List<SysMenu> getChildMenus(Long id, Long roleId, List<SysMenu> list) {
        ArrayList<SysMenu> childs = new ArrayList<>();
        for (SysMenu source : list) {
            if(source.getParentId()==id){ //找儿子
                childs.add(source);
                source.setChilds(getChildMenus(source.getId(),roleId,list));
                List<SysPrivilege> sysPrivileges = sysPrivilegeService.getAllSysPrivilege(source.getId(),roleId);
                source.setPrivileges(sysPrivileges);
            }
        }
        return childs;
    }
}
