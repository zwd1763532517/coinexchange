package com.bjsxt.service;

import com.bjsxt.domain.Sms;
import com.baomidou.mybatisplus.extension.service.IService;
public interface SmsService extends IService<Sms>{


    /**
     * 发送手机验证码
     * @param sms
     * @return
     */
    boolean sendMsg(Sms sms);
}
