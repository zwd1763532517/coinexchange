package com.bjsxt.service;

import com.bjsxt.model.dto.LoginFormDTO;
import com.bjsxt.model.vo.LoginUserVO;

public interface LoginService {

    /**
     *
     * @param loginForm
     * @return
     */
    LoginUserVO login(LoginFormDTO loginForm);
}
