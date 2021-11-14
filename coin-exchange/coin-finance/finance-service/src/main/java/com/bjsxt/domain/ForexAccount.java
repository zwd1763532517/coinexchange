package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.ForexAccount")
@Data
@TableName(value = "forex_account")
public class ForexAccount {
    /**
     * 主键
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 交易对ID
     */
    @TableField(value = "market_id")
    @ApiModelProperty(value="交易对ID")
    private Long marketId;

    /**
     * 交易对
     */
    @TableField(value = "market_name")
    @ApiModelProperty(value="交易对")
    private String marketName;

    /**
     * 持仓方向：1-买；2-卖
     */
    @TableField(value = "type")
    @ApiModelProperty(value="持仓方向：1-买；2-卖")
    private Byte type;

    /**
     * 持仓量
     */
    @TableField(value = "amount")
    @ApiModelProperty(value="持仓量")
    private BigDecimal amount;

    /**
     * 冻结持仓量
     */
    @TableField(value = "lock_amount")
    @ApiModelProperty(value="冻结持仓量")
    private BigDecimal lockAmount;

    /**
     * 状态：1-有效；2-锁定；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：1-有效；2-锁定；")
    private Byte status;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time")
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    @ApiModelProperty(value="创建时间")
    private Date created;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_MARKET_ID = "market_id";

    public static final String COL_MARKET_NAME = "market_name";

    public static final String COL_TYPE = "type";

    public static final String COL_AMOUNT = "amount";

    public static final String COL_LOCK_AMOUNT = "lock_amount";

    public static final String COL_STATUS = "status";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_CREATED = "created";
}