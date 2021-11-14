package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.UserCoinFreeze")
@Data
@TableName(value = "user_coin_freeze")
public class UserCoinFreeze {
     @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="null")
    private Long userId;

    @TableField(value = "coin_id")
    @ApiModelProperty(value="null")
    private Long coinId;

    @TableField(value = "freeze")
    @ApiModelProperty(value="null")
    private Long freeze;

    public static final String COL_COIN_ID = "coin_id";

    public static final String COL_FREEZE = "freeze";
}