package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.domain.Config;

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

    /**
     *
     * @param sign
     * @return
     */
    Config getConfigByCode(String sign);
}
