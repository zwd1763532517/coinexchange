package com.bjsxt.model.dto;

import com.bjsxt.geetest.GeetestForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@ApiModel(value = "登录参数")
@Slf4j
public class LoginFormDTO extends GeetestForm {
    @ApiModelProperty(value = "电话的国家区号")
    private String countryCode ;

    @ApiModelProperty(value = "用户名称")
    private String username ;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户的UUID")
    private String uuid ;
}
