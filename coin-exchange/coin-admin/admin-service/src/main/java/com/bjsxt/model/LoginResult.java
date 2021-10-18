package com.bjsxt.model;

import com.bjsxt.domain.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {

    /**
     * 登录返回的token
     */
    private String token;

    /**
     * 前端菜单数据
     */
    private List<SysMenu> menus;

    /**
     * 权限数据
     */
    private List<SimpleGrantedAuthority> authorities;
}
