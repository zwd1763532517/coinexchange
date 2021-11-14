package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.ForexCoin")
@Data
@TableName(value = "forex_coin")
public class ForexCoin {
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 币种名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="币种名称")
    private String name;

    /**
     * 币种标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="币种标题")
    private String title;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Byte sort;

    /**
     * 状态: 0禁用 1启用
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态: 0禁用 1启用")
    private Boolean status;

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

    public static final String COL_NAME = "name";

    public static final String COL_TITLE = "title";

    public static final String COL_SORT = "sort";

    public static final String COL_STATUS = "status";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_CREATED = "created";
}