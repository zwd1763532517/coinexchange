package com.bjsxt.service.impl;

import com.bjsxt.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.bjsxt.constant.LoginConstant.*;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String loginType = requestAttributes.getRequest().getParameter("login_type");
        if (StringUtils.isEmpty(loginType)) {
            throw new AuthenticationServiceException("请添加login_type参数");
        }
        UserDetails userDetails = null;
        String grantType = requestAttributes.getRequest().getParameter("grant_type");
        try {
            if (LoginConstant.REFRESH_TOKEN.equals(grantType.toUpperCase())) {
                username = adjustUsername(username, loginType); // 为refresh_token 时，需要将id->username
            }
            switch (loginType) {
                case LoginConstant.ADMIN_TYPE: // 管理员登录
                    userDetails = loadAdminUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE: // 会员登录
                    userDetails = loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式" + loginType);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new UsernameNotFoundException("会员：" + username + "不存在");
        }
        return userDetails;
    }

    private String adjustUsername(String username, String loginType) {
        if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    private UserDetails loadMemberUserByUsername(String username) {
        return jdbcTemplate.queryForObject(QUERY_MEMBER_SQL, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                if(rs.wasNull()){
                    throw new UsernameNotFoundException("会员：" + username + "不存在");
                }
                long id = rs.getLong("id"); // 获取用户的id
                String password = rs.getString("password");
                int status = rs.getInt("status");
                return new User(
                        String.valueOf(id),
                        password,
                        status == 1 ,
                        true ,
                        true ,
                        true,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
        }, username);

    }

    private UserDetails loadAdminUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.wasNull()) {
                    throw new UsernameNotFoundException("用户：" + username + "不存在");
                }
                Long id = rs.getLong("id");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                User user = new User(
                        String.valueOf(id), // 使用用户的id 代替用户的名称，这样会使得后面的很多情况得以处理
                        password,
                        status == 1,
                        true,
                        true,
                        true,
                        getUserPermissions(id));
                return user;
            }
        }, username);
    }

    private Set<SimpleGrantedAuthority> getUserPermissions(Long id) {
        // 查询用户是否为管理员
        String code = jdbcTemplate.queryForObject(QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null;
        if (LoginConstant.ADMIN_CODE.equals(code)) { // 管理员
            permissions = jdbcTemplate.queryForList(QUERY_ALL_PERMISSIONS, String.class);
        } else {
            permissions = jdbcTemplate.queryForList(QUERY_PERMISSION_SQL, String.class, id);
        }
        if (permissions == null || permissions.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        return permissions
                .stream()
                .distinct() // 去重
                .map(
                        perm -> new SimpleGrantedAuthority(perm) // perm - >security可以识别的权限
                )
                .collect(Collectors.toSet());
    }
}
