package com.bjsxt.service;

import com.bjsxt.domain.UserAuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserAuthInfoService extends IService<UserAuthInfo>{


    /**
     *
     * @param authCode
     * @return
     */
    List<UserAuthInfo> getUserAuthInfoByCode(Long authCode);

    /**
     *
     * @param id
     * @return
     */
    List<UserAuthInfo> getUserAuthInfoByUserId(Long id);
}
