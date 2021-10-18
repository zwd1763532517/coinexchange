package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.mapper.WebConfigMapper;
import com.bjsxt.domain.WebConfig;
import com.bjsxt.service.WebConfigService;
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

    @Override
    public Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type) {
        return this.page(page,new LambdaQueryWrapper<WebConfig>()
                .like(StringUtils.isNotEmpty(name),WebConfig::getName,name)
                .eq(StringUtils.isNotEmpty(type),WebConfig::getType,type)
        );
    }
}
