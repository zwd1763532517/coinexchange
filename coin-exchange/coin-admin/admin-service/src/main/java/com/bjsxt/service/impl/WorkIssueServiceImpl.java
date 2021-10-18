package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.WorkIssue;
import com.bjsxt.mapper.WorkIssueMapper;
import com.bjsxt.service.WorkIssueService;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

    @Override
    public Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime) {
        return this.page(page,new LambdaQueryWrapper<WorkIssue>()
                .eq(status != null,WorkIssue::getStatus,status)
                .between(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime),WorkIssue::getCreated,startTime,endTime+" 23:59:59")
        );
    }
}
