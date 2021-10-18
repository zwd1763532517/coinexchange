package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.Notice;
import com.bjsxt.mapper.NoticeMapper;
import com.bjsxt.service.NoticeService;
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

    @Override
    public Page<Notice> findByPage(Page<Notice> page, String title, String startTime, String endTime, Integer status) {
        return this.page(page,new LambdaQueryWrapper<Notice>()
                .like(StringUtils.isNotEmpty(title),Notice::getTitle,title)
                .between(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime),Notice::getCreated,startTime,endTime+" 23:59:59")
                .eq(status!=null,Notice::getStatus,status)
        );
    }
}
