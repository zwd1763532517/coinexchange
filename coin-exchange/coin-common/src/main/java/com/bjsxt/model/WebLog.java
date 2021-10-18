package com.bjsxt.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * web 操作日志记录
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class WebLog {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 开始时间
     */
    private long recodeTime;

    /**
     * 消耗时间
     */
    private long spendTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 返回结果
     */
    private Object result;

}
