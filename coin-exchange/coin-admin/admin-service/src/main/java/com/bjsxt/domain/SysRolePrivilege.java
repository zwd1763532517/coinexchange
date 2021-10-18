package com.bjsxt.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com.bjsxt.domain.SysRolePrivilege")
@Data
@TableName(value = "sys_role_privilege")
public class SysRolePrivilege {
     @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="null")
    private Long id;

    @TableField(value = "role_id")
    @ApiModelProperty(value="null")
    private Long roleId;

    @TableField(value = "privilege_id")
    @ApiModelProperty(value="null")
    private Long privilegeId;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_PRIVILEGE_ID = "privilege_id";
}