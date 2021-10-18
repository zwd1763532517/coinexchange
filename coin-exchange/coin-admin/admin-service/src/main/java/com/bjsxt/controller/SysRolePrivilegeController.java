package com.bjsxt.controller;

import com.bjsxt.domain.SysMenu;
import com.bjsxt.model.RolePrivilegesDto;
import com.bjsxt.response.R;
import com.bjsxt.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "角色权限的配置")
public class SysRolePrivilegeController {


    @Autowired
    private SysRolePrivilegeService sysRolePrivilegeService;


    @GetMapping("/roles_privileges")
    @ApiOperation(value = "根据角色id查看所具有的权限")
    //@PreAuthorize("hasAuthority('sys_role_')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "角色id")
    })
    public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId){
        return R.ok(sysRolePrivilegeService.findSysMenuAndPrivileges(roleId));
    }

    @PostMapping("/grant_privileges")
    @ApiOperation(value = "修改角色权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rolePrivilegesDto",value = "rolePrivilegesDto")
    })
    public R grantPrivileges(@RequestBody RolePrivilegesDto dto){
        boolean isOk = sysRolePrivilegeService.grantPrivileges(dto);
        if(isOk){
            return R.ok();
        }
        return R.fail();
    }


}
