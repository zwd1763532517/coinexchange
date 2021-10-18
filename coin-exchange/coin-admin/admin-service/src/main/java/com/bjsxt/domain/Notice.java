package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.Notice")
@Data
@TableName(value = "notice")
public class Notice {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 简介
     */
    @TableField(value = "description")
    @ApiModelProperty(value="简介")
    private String description;

    /**
     * 作者
     */
    @TableField(value = "author")
    @ApiModelProperty(value="作者")
    private String author;

    /**
     * 文章状态
     */
    @TableField(value = "status")
    @ApiModelProperty(value="文章状态")
    private Integer status;

    /**
     * 文章排序，越大越靠前
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="文章排序，越大越靠前")
    private Integer sort;

    /**
     * 内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="内容")
    private String content;

    /**
     * 最后修改时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="最后修改时间")
    private Date lastUpdateTime;

    /**
     * 创建日期
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建日期")
    private Date created;

    public static final String COL_TITLE = "title";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_AUTHOR = "author";

    public static final String COL_STATUS = "status";

    public static final String COL_SORT = "sort";

    public static final String COL_CONTENT = "content";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";

    public static final String COL_CREATED = "created";
}