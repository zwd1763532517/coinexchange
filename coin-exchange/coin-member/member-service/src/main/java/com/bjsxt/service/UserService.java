package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.dto.UserDto;
import com.bjsxt.model.dto.RegisterParamDTO;
import com.bjsxt.model.dto.UpdatePhoneParamDTO;
import com.bjsxt.model.dto.UserAuthFormDTO;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User>{


    /**
     *
     * @param page
     * @param mobile
     * @param userId
     * @param userName
     * @param realName
     * @param status
     * @return
     */
    Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status,Integer reviewsStatus);

    /**
     *
     * @param page
     * @param userId
     * @return
     */
    Page<User> findDirPage(Page<User> page, Long userId);

    /**
     * 修改审核状态
     * @param id
     * @param authStatus
     * @param authCode
     * @param remark
     */
    void updateUserAuthStatus(Long id, Byte authStatus, Long authCode, String remark);

    /**
     *
     * @param ids
     * @return
     */
    Map<Long,UserDto> getBasicUsers(List<Long> ids, String userName, String mobile);

    /**
     *
     * @param aLong
     * @param userAuthForm
     * @return
     */
    boolean identifyVerify(Long aLong, UserAuthFormDTO userAuthForm);

    /**
     *
     * @param aLong
     * @param updatePhoneParam
     * @return
     */
    boolean updatePhone(Long aLong, UpdatePhoneParamDTO updatePhoneParam);

    /**
     * 检验新的手机号是否可用,若可用,则给新的手机号发送一个验证码
     *
     * @param mobile      新的手机号
     * @param countryCode 国家代码
     * @return
     */
    boolean checkNewPhone(String mobile, String countryCode);

    /**
     *
     * @param registerParam
     * @return
     */
    boolean register(RegisterParamDTO registerParam);
}
