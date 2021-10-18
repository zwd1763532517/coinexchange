package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysPrivilege;
import com.bjsxt.response.R;
import com.bjsxt.service.SysPrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/privileges")
@Api(tags = "权限管理")
public class SysPrivilegeController {

    @Autowired
    private SysPrivilegeService sysPrivilegeService;

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页显示的条数"),
    })
    @PreAuthorize("hasAuthority('sys_privilege_query')")
    public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
        return R.ok(sysPrivilegePage);
    }

    @PostMapping
    @ApiOperation(value = "新增权限")
    @PreAuthorize("hasAuthority('sys_privilege_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege",value = "sysPrivilege 的json数据")
    })
    public R add (@RequestBody @Validated SysPrivilege sysPrivilege){
        //String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //sysPrivilege.setCreateBy(Long.valueOf(userIdStr));
        //sysPrivilege.setCreated(new Date());
        //sysPrivilege.setLastUpdateTime(new Date());
        boolean save = sysPrivilegeService.save(sysPrivilege);
        if(save){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改权限")
    @PreAuthorize("hasAuthority('sys_privilege_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysPrivilege",value = "sysPrivilege 数据")
    })
    public R update(@RequestBody @Validated SysPrivilege sysPrivilege){
        //String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        //sysPrivilege.setModifyBy(Long.valueOf(userIdStr));
        //sysPrivilege.setLastUpdateTime(new Date());
        boolean save = sysPrivilegeService.updateById(sysPrivilege);
        if(save){
            return R.ok();
        }
        return R.fail();
    }

}
