package com.bjsxt.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@ApiModel(value = "角色和对应的权限实体")
public class RolePrivilegesDto {

    @ApiModelProperty(value = "角色id")
    private Long roleId;

    @ApiModelProperty(value = "权限集合")
    private List<Long> privilegeIds = Collections.emptyList();
}
