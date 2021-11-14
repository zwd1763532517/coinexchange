package com.bjsxt.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.UserAddress;
import com.bjsxt.response.R;
import com.bjsxt.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/userAddress")
@Api(tags = "用户钱包地址")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping
    @ApiOperation(value = "查询钱包地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "用户id"),
            @ApiImplicitParam(name = "current",value = "当前页"),
            @ApiImplicitParam(name = "size",value = "大小"),
    })
    public R<Page<UserAddress>> findByPage(@ApiIgnore Page<UserAddress> page,Long userId){
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<UserAddress> userAddressPage = userAddressService.findByPage(page,userId);
        return R.ok(userAddressPage);
    }
}
