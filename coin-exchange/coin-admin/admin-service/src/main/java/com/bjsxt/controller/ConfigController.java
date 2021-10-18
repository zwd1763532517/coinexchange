package com.bjsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.Config;
import com.bjsxt.response.R;
import com.bjsxt.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "系统参数管理")
@RequestMapping("/configs")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping
    @ApiOperation(value = "条件查询后台参数配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "后台参数类型"),
            @ApiImplicitParam(name = "code",value = "后台参数类型"),
            @ApiImplicitParam(name = "name",value = "后台参数类型"),
            @ApiImplicitParam(name = "current",value = "后台参数类型"),
            @ApiImplicitParam(name = "size",value = "后台参数类型"),
    })
    @PreAuthorize("hasAuthority('config_query')")
    public R<Page<Config>> findByPage(@ApiIgnore Page<Config> page,String type,String code,String name){
        Page<Config> configPage = configService.findByPage(page,type,name,code);
        return R.ok(configPage);
    }

    @PostMapping
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "config",value = "数据")
    })
    @PreAuthorize("hasAuthority('config_create')")
    public R add(@RequestBody @Validated Config config){
        boolean save = configService.save(config);
        if (save){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "config",value = "数据")
    })
    @PreAuthorize("hasAuthority('config_update')")
    public R update(@RequestBody @Validated Config config){
        boolean b = configService.updateById(config);
        if (b){
            return R.ok();
        }
        return R.fail();
    }
}
