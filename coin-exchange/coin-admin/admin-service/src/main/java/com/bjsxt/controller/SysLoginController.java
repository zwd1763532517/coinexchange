package com.bjsxt.controller;

import com.bjsxt.model.LoginResult;
import com.bjsxt.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录控制器")
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @PostMapping("/login")
    @ApiOperation(value = "后台管理员登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "username", value = "用户名称"),
                    @ApiImplicitParam(name = "password", value = "密码"),
            }
    )
    public LoginResult login(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) String password
    ){
        return loginService.login(username,password);
    }

}
