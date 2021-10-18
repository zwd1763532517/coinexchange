package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysRole;
import com.bjsxt.response.R;
import com.bjsxt.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/roles")
@Api(tags = "角色管理")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;


    @GetMapping
    @ApiOperation(value = "角色条件分页查询")
    @PreAuthorize("hasAuthority('sys_role_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页显示的大小"),
            @ApiImplicitParam(name = "name",value = "角色名称"),
    })
    public R<Page<SysRole>> findByPage(Page<SysRole> page, String name){
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(sysRoleService.findByPage(page,name));
    }


    @PostMapping
    @ApiOperation(value = "新增权限")
    @PreAuthorize("hasAuthority('sys_role_create')")
    public R addRole(@RequestBody @Validated SysRole sysRole){
        boolean save = sysRoleService.save(sysRole);
        if(save){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除角色")
    @PreAuthorize("hasAuthority('sys_role_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "要删除角色的id的集合")
    })
    public R delete(@RequestBody String []ids){
        if(ids==null || ids.length==0){
            return R.fail("id不能为空");
        }
        boolean b = sysRoleService.removeByIds(Arrays.asList(ids));
        if(b){
            return R.ok();
        }
        return R.fail();
    }

}
