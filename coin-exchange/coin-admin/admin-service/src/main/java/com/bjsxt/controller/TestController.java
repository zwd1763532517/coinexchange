package com.bjsxt.controller;

import com.bjsxt.domain.SysUser;
import com.bjsxt.response.R;
import com.bjsxt.service.SysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "admin-service测试")
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/user/info/{id}")
    @ApiOperation(value = "使用id查询用户",authorizations = {@Authorization("Authorization")})
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "用户的id")})
    public R<SysUser> sysUser(@PathVariable("id") Long id){
        SysUser sysUser = sysUserService.getById(id);
        return R.ok(sysUser);
    }
}
