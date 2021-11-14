package com.bjsxt.controller;

import com.bjsxt.domain.Sms;
import com.bjsxt.response.R;
import com.bjsxt.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@Api(tags = "sms短信发送")
public class SmsController {

    @Autowired
    private SmsService smsService;

    @GetMapping("/sendTo")
    @ApiOperation(value = "发送短信")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sms", value = "短信的json数据")
    })
    public R sendMsg(@RequestBody @Validated Sms sms) {
        boolean isOk = smsService.sendMsg(sms);
        if (isOk) {
            return R.ok();
        }
        return R.fail("发送失败");
    }

}
