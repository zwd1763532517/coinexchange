package com.bjsxt.service.impl;

import com.bjsxt.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.SysMenuMapper;
import com.bjsxt.domain.SysMenu;
import com.bjsxt.service.SysMenuService;
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService{

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        if(sysRoleService.isSuperAdmin(userId)){
            return list();
        }
        return sysMenuMapper.selectMenusByUserId(userId);
    }
}
