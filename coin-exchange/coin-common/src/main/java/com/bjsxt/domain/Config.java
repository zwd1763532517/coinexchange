package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value="com.bjsxt.domain.Config")
@Data
@TableName(value = "config")
public class Config {
    /**
     * 主键
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 配置规则类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value="配置规则类型")
    private String type;

    /**
     * 配置规则代码
     */
    @TableField(value = "code")
    @ApiModelProperty(value="配置规则代码")
    private String code;

    /**
     * 配置规则名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="配置规则名称")
    private String name;

    /**
     * 配置规则描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value="配置规则描述")
    private String desc;

    /**
     * 配置值
     */
    @TableField(value = "value")
    @ApiModelProperty(value="配置值")
    private String value;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    public static final String COL_TYPE = "type";

    public static final String COL_CODE = "code";

    public static final String COL_NAME = "name";

    public static final String COL_DESC = "desc";

    public static final String COL_VALUE = "value";

    public static final String COL_CREATED = "created";
}