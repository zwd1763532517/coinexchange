package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Notice;
import com.bjsxt.response.R;
import com.bjsxt.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@Api(tags = "公告管理")
@RequestMapping("/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    @ApiOperation(value = "分页查询公告")
    @PreAuthorize("hasAuthority('notice_query')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "当前页"),
            @ApiImplicitParam(name = "title",value = "当前页"),
            @ApiImplicitParam(name = "startTime",value = "当前页"),
            @ApiImplicitParam(name = "endTime",value = "当前页"),

    })
    public R<Page<Notice>> findByPage(
            @ApiIgnore Page<Notice> page, String title,String startTime,
            String endTime,Integer status){
        page.addOrder(OrderItem.desc("last_update_time"));
        return R.ok(noticeService.findByPage(page,title,startTime,endTime,status));
    }


    @PostMapping("/delete")
    @ApiOperation(value = "删除公告")
    @PreAuthorize("hasAuthority('notice_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids",value = "id集合")
    })
    public R delete(@RequestBody String []ids){
        if (ids==null||ids.length==0){
            return R.fail();
        }
        boolean b = noticeService.removeByIds(Arrays.asList(ids));
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "启用/禁用公告")
    @PreAuthorize("hasAuthority('notice_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "公告id"),
            @ApiImplicitParam(name = "status",value = "要设置的公告状态")
    })
    public R updateStatus(Long id, Integer status){
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(status);
        boolean b = noticeService.updateById(notice);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping
    @ApiOperation(value = "新增公告")
    @PreAuthorize("hasAuthority('notice_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice",value = "notice数据")
    })
    public R delete(@RequestBody Notice notice){
        notice.setStatus(1);
        boolean save = noticeService.save(notice);
        if (save){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改公告")
    @PreAuthorize("hasAuthority('notice_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "notice",value = "notice数据")
    })
    public R update(@RequestBody Notice notice){
        boolean b = noticeService.updateById(notice);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

}
