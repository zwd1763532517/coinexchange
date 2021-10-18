package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@ApiModel(value="com.bjsxt.domain.SysUser")
@Data
@TableName(value = "sys_user")
public class SysUser {
    /**
     * 主键
     */
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 账号
     */
    @TableField(value = "username")
    @ApiModelProperty(value="账号")
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value="密码")
    @NotBlank
    private String password;

    /**
     * 姓名
     */
    @TableField(value = "fullname")
    @ApiModelProperty(value="姓名")
    @NotBlank
    private String fullname;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号")
    @NotBlank
    private String mobile;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="邮箱")
    @NotBlank
    private String email;

    /**
     * 状态 0-无效； 1-有效；
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态 0-无效； 1-有效；")
    private Byte status;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @TableField(value = "modify_by",fill = FieldFill.UPDATE)
    @ApiModelProperty(value="修改人")
    private Long modifyBy;

    /**
     * 创建时间
     */
    @TableField(value = "created",fill = FieldFill.INSERT)
    @ApiModelProperty(value="创建时间")
    private Date created;

    /**
     * 修改时间
     */
    @TableField(value = "last_update_time",fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="修改时间")
    private Date lastUpdateTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色的ids")
    private String role_strings;

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_FULLNAME = "fullname";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_EMAIL = "email";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATE_BY = "create_by";

    public static final String COL_MODIFY_BY = "modify_by";

    public static final String COL_CREATED = "created";

    public static final String COL_LAST_UPDATE_TIME = "last_update_time";
}