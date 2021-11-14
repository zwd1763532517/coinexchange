package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserWallet;
import com.bjsxt.response.R;
import com.bjsxt.service.UserWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "用户的提币地址")
@RequestMapping("/userWallets")
public class UserWalletController {

    @Autowired
    private UserWalletService userWalletService;

    @GetMapping
    @ApiOperation(value = "查询提币地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    @PreAuthorize("hasAuthority('user_wallet_query')")
    public R<Page<UserWallet>> findByPage(@ApiIgnore Page<UserWallet> page,Long userId){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<UserWallet> userWalletPage = userWalletService.findByPage(page,userId);
        return R.ok(userWalletPage);
    }

}
