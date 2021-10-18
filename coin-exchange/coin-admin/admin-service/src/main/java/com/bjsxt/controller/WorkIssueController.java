package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.WorkIssue;
import com.bjsxt.response.R;
import com.bjsxt.service.WorkIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "客服工单")
@RequestMapping("/workIssues")
public class WorkIssueController {

    @Autowired
    private WorkIssueService workIssueService;


    @GetMapping
    @ApiOperation(value = "工单查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "startTime",value = "状态"),
            @ApiImplicitParam(name = "endTime",value = "状态"),
            @ApiImplicitParam(name = "current",value = "状态"),
            @ApiImplicitParam(name = "size",value = "状态"),
    })
    @PreAuthorize("hasAuthority('work_issue_query')")
    public R<Page<WorkIssue>> findByPage(Page<WorkIssue> page,Integer status,String startTime,String endTime){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<WorkIssue> workIssuePage = workIssueService.findByPage(page,status,startTime,endTime);
        return R.ok(workIssuePage);
    }

    @PatchMapping
    @ApiOperation(value = "回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "状态"),
            @ApiImplicitParam(name = "startTime",value = "状态")
    })
    @PreAuthorize("hasAuthority('work_issue_update')")
    public R update(Long id,String answer){
        WorkIssue workIssue = new WorkIssue();
        workIssue.setId(id);
        workIssue.setAnswer(answer);
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        workIssue.setAnswerUserId(Long.valueOf(userIdStr));
        boolean b = workIssueService.updateById(workIssue);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

}
