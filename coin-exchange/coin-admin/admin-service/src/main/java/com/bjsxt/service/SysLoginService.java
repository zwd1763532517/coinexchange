package com.bjsxt.service;

import com.bjsxt.model.LoginResult;

public interface SysLoginService {

    LoginResult login(String username,String password);
}
