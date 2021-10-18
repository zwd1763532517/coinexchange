package com.bjsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.WebConfig;
import com.bjsxt.response.R;
import com.bjsxt.service.WebConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

@RestController
@Api(tags = "资源配置管理")
@RequestMapping("/webConfigs")
public class WebConfigController {

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "名称"),
            @ApiImplicitParam(name = "type",value = "类型"),
            @ApiImplicitParam(name = "current",value = "第几页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    @PreAuthorize("hasAuthority('web_config_query')")
    public R<Page<WebConfig>> findByPage(@ApiIgnore Page<WebConfig> page, String name, String type){
        Page<WebConfig> webConfigPage = webConfigService.findByPage(page,name,type);
        return R.ok(webConfigPage);
    }

    @PostMapping
    @ApiOperation(value = "新增")
    @PreAuthorize("hasAuthority('web_config_create')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig",value = "数据")
    })
    public R add(@RequestBody @Validated WebConfig webConfig){
        boolean save = webConfigService.save(webConfig);
        if (save){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改")
    @PreAuthorize("hasAuthority('web_config_update')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig",value = "数据")
    })
    public R update(@RequestBody @Validated WebConfig webConfig){
        boolean b = webConfigService.updateById(webConfig);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除")
    @PreAuthorize("hasAuthority('web_config_delete')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "webConfig",value = "数据")
    })
    public R update(@RequestBody String[] ids){
        boolean b = webConfigService.removeByIds(Arrays.asList(ids));
        if (b){
            return R.ok();
        }
        return R.fail();
    }

}
