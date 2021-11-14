package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserBank;
import com.bjsxt.response.R;
import com.bjsxt.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "会员银行卡管理")
@RequestMapping("/userBanks")
public class UserBankController {

    @Autowired
    private UserBankService userBankService;

    @GetMapping
    @ApiOperation(value = "查询会员的银行卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "会员id"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    @PreAuthorize("hasAuthority('user_bank_query')")
    public R<Page<UserBank>> findByPage(@ApiIgnore Page<UserBank> page,Long userId)
    {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<UserBank> userBankPage = userBankService.findByPage(page,userId);
        return R.ok(userBankPage);
    }

    @PostMapping("/status")
    @ApiOperation(value = "修改银行卡状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "银行卡id"),
            @ApiImplicitParam(name = "status",value = "状态")
    })
    public R updateStatus(
           Long id,Byte status
    ){
        UserBank userBank = new UserBank();
        userBank.setId(id);
        userBank.setStatus(status);
        boolean b = userBankService.updateById(userBank);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @PatchMapping
    @ApiOperation(value = "修改银行卡信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userBank",value = "银行卡")
    })
    public R update(
            @RequestBody @Validated UserBank userBank
    ){
        boolean b = userBankService.updateById(userBank);
        if (b){
            return R.ok();
        }
        return R.fail();
    }

    @GetMapping("/current")
    @ApiOperation(value = "查询用户的卡号")
    public R<UserBank> currentUserBank(){
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserBank userBank =  userBankService.getUserBankByUserId(Long.valueOf(idStr)) ;
        return R.ok(userBank) ;
    }

    @PostMapping("/bind")
    @ApiOperation(value = "绑定银行卡")
    public  R bindBank(@RequestBody @Validated UserBank userBank){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean isOk = userBankService.bindBank(userId,userBank) ;
        if(isOk){
            return R.ok() ;
        }
        return R.fail("绑定失败") ;
    }

}
