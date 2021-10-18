package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;
public interface WebConfigService extends IService<WebConfig>{


    /**
     * 获取资源配置列表
     * @param page
     * @param name
     * @param type
     * @return
     */
    Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type);
}
