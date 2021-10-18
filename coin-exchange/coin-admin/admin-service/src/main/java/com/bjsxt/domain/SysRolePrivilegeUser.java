package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.SysRolePrivilegeUser")
@Data
@TableName(value = "sys_role_privilege_user")
public class SysRolePrivilegeUser {
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="null")
    private Long id;

    /**
     * 角色Id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色Id")
    private Long roleId;

    /**
     * 用户Id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户Id")
    private Long userId;

    /**
     * 权限Id
     */
    @TableField(value = "privilege_id")
    @ApiModelProperty(value="权限Id")
    private Long privilegeId;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_PRIVILEGE_ID = "privilege_id";
}