package com.bjsxt.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "修改手机号")public class UpdatePhoneParamDTO {


    @ApiModelProperty(value = "国家的Code")
    @NotBlank
    private String countryCode ;

    @ApiModelProperty(value = "新的手机号")
    @NotBlank
    private String newMobilePhone ;

    @ApiModelProperty(value = "新手机号的验证码")
    @NotBlank
    private String validateCode ;


    @ApiModelProperty(value = "旧手机号的验证码")
    @NotBlank
    private String oldValidateCode ;
}
