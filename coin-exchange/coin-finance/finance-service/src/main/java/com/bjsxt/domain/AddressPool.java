package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.AddressPool")
@Data
@TableName(value = "address_pool")
public class AddressPool {
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 币种ID
     */
    @TableField(value = "coin_id")
    @ApiModelProperty(value="币种ID")
    private Long coinId;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value="地址")
    private String address;

    /**
     * keystore
     */
    @TableField(value = "keystore")
    @ApiModelProperty(value="keystore")
    private String keystore;

    /**
     * 密码
     */
    @TableField(value = "pwd")
    @ApiModelProperty(value="密码")
    private String pwd;

    /**
     * 地址类型
     */
    @TableField(value = "coin_type")
    @ApiModelProperty(value="地址类型")
    private String coinType;

    public static final String COL_COIN_ID = "coin_id";

    public static final String COL_ADDRESS = "address";

    public static final String COL_KEYSTORE = "keystore";

    public static final String COL_PWD = "pwd";

    public static final String COL_COIN_TYPE = "coin_type";
}