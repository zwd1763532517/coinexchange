package com.bjsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.AdminBank;
import com.bjsxt.dto.AdminBankDto;
import com.bjsxt.feign.AdminBankServiceFeign;
import com.bjsxt.response.R;
import com.bjsxt.service.AdminBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "银行卡配置")
@RequestMapping("/adminBanks")
public class AdminBankController implements AdminBankServiceFeign {

    @Autowired
    private AdminBankService adminBankService;


    @GetMapping
    @ApiOperation(value = "查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankCard",value = "公司的银行卡"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    @PreAuthorize("hasAuthority('admin_bank_query')")
    public R<Page<AdminBank>> findByPage(Page<AdminBank> page,String bankCard){
        Page<AdminBank> adminBankPage = adminBankService.findByPage(page,bankCard);
        return R.ok(adminBankPage);
    }

    @PostMapping
    @ApiOperation(value = "新增")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank",value = "数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_create')")
    public R add(@RequestBody @Validated AdminBank adminBank){
        boolean save = adminBankService.save(adminBank);
        if (save){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank",value = "数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R update(@RequestBody @Validated AdminBank adminBank){
        boolean b = adminBankService.updateById(adminBank);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @PostMapping("/adminUpdateBankStatus")
    @ApiOperation(value = "修改银行卡状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId",value = "数据"),
            @ApiImplicitParam(name = "status",value = "数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R changeStatus(Long bankId,Byte status){
        AdminBank adminBank = new AdminBank();
        adminBank.setId(bankId);
        adminBank.setStatus(status);
        boolean b = adminBankService.updateById(adminBank);
        if (b) {
            return R.ok();
        }
        return R.fail();
    }


    @Override
    public List<AdminBankDto> getAllAdminBanks() {
        List<AdminBankDto> adminBankDtos = adminBankService.getAllAdminBanks() ;
        return adminBankDtos;
    }
}
