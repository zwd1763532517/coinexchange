package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.UserAccountFreeze")
@Data
@TableName(value = "user_account_freeze")
public class UserAccountFreeze {
     @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="null")
    private Long userId;

    @TableField(value = "freeze")
    @ApiModelProperty(value="null")
    private BigDecimal freeze;

    public static final String COL_FREEZE = "freeze";
}