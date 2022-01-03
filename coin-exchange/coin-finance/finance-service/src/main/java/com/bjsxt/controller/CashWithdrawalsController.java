package com.bjsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.CashWithdrawals;
import com.bjsxt.model.param.CashSellParam;
import com.bjsxt.response.R;
import com.bjsxt.service.CashWithdrawalsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(tags = "充值控制器")
public class CashWithdrawalsController {

    @Autowired
    private CashWithdrawalsService cashWithdrawalsService;

    @GetMapping("/user/records")
    @ApiOperation(value = "查询当前用户的充值记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页显示的大小") ,
            @ApiImplicitParam(name = "status",value = "充值的状态") ,
    })
    public R<Page<CashWithdrawals>> findUserCashRecharge(@ApiIgnore Page<CashWithdrawals> page , Byte status){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<CashWithdrawals> cashWithdrawalsPage = cashWithdrawalsService.findCashWithdrawals(page ,userId,status) ;
        return R.ok(cashWithdrawalsPage) ;
    }

    @PostMapping("/sell")
    @ApiOperation(value = "GCN的卖出操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "CashSellParam", value = "cashSellParam")
    })
    public R<Object> sell(@RequestBody @Validated CashSellParam cashSellParam){

        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        boolean isOk = cashWithdrawalsService.sell(userId, cashSellParam);
        if (isOk) {
            return R.ok("提交申请成功");
        }
        return R.fail("提交申请失败");
    }


}
