package com.bjsxt.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 身份认证
 */
@ConfigurationProperties(prefix = "identify")
@Data
public class IdProperties {


    /**
     * 对应你购买的appKey
     */
    private String appKey ;

    /**
     * 对应你购买的appSecret
     */
    private String appSecret ;


    /**
     * 对应你购买的appCode
     */
    private String appCode ;

    /**
     * 认证的url地址
     */
    private String url ;
}
