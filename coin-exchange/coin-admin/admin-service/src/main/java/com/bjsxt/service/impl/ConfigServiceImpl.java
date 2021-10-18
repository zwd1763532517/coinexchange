package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.Config;
import com.bjsxt.mapper.ConfigMapper;
import com.bjsxt.service.ConfigService;
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService{

    @Override
    public Page<Config> findByPage(Page<Config> page, String type, String name, String code) {
        return this.page(page,new LambdaQueryWrapper<Config>()
                .like(StringUtils.isNotEmpty(type),Config::getType,type)
                .like(StringUtils.isNotEmpty(name),Config::getName,name)
                .like(StringUtils.isNotEmpty(code),Config::getCode,code)
        );
    }
}
