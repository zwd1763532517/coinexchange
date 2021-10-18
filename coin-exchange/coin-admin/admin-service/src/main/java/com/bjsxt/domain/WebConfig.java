package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.WebConfig")
@Data
@TableName(value = "web_config")
public class WebConfig {
    /**
     * Id
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="Id")
    private Long id;

    /**
     * 分组, LINK_BANNER ,WEB_BANNER
     */
    @TableField(value = "type")
    @ApiModelProperty(value="分组, LINK_BANNER ,WEB_BANNER")
    private String type;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 值
     */
    @TableField(value = "value",condition = "test")
    @ApiModelProperty(value="值")
    private String value = "test";

    /**
     * 权重
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="权重")
    private Short sort;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 超链接地址
     */
    @TableField(value = "url",exist = false)
    @ApiModelProperty(value="超链接地址")
    private String url;

    /**
     * 是否使用 0 否 1是
     */
    @TableField(value = "status")
    @ApiModelProperty(value="是否使用 0 否 1是")
    private Integer status;

    public static final String COL_TYPE = "type";

    public static final String COL_NAME = "name";

    public static final String COL_VALUE = "value";

    public static final String COL_SORT = "sort";

    public static final String COL_CREATED = "created";

    public static final String COL_URL = "url";

    public static final String COL_STATUS = "status";
}