package com.bjsxt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {

    /**
     * 获取用户的对象
     * */
    @GetMapping("/user/info")
    public Principal userInfo(Principal principal){  //principal是由oauth2自动注入的
        // 原理就是：利用Context概念，将授权用户放在线程里面，利用ThreadLocal来获取当前的用户对象
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return principal;
    }
}
