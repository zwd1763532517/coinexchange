package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Config;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ConfigService extends IService<Config>{


    /**
     * 获取配置列表
     * @param page
     * @param type
     * @param name
     * @param code
     * @return
     */
    Page<Config> findByPage(Page<Config> page, String type, String name, String code);
}
