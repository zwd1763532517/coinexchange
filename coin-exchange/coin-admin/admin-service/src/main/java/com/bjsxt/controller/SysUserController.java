package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.SysUser;
import com.bjsxt.response.R;
import com.bjsxt.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "每页大小"),
            @ApiImplicitParam(name = "mobile",value = "手机号码"),
            @ApiImplicitParam(name = "fullname",value = "用户全名称"),
    })
    @PreAuthorize("hasAuthority('sys_user_query')")
    public R<Page<SysUser>> findUsersByPage(Page<SysUser> page,String mobile, String fullname){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<SysUser> usersByPage = sysUserService.findUsersByPage(page, mobile, fullname);
        return R.ok(usersByPage);
    }

    @PostMapping
    @ApiOperation(value = "新增员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser",value = "sysUser数据")
    })
    public R addUser(@RequestBody SysUser sysUser){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        sysUser.setCreateBy(userId);
        boolean isOk = sysUserService.addUser(sysUser);
        if(isOk){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除用户")
    public R deleteUser(@RequestBody Long ids[]){
        boolean b = sysUserService.removeByIds(Arrays.asList(ids));
        if(b){
            return R.ok();
        }
        return R.fail();
    }



}
