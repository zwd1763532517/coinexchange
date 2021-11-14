package com.bjsxt.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "登录返回")
public class LoginUserVO {

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "有效期")
    private Long expiresIn;

    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
}
