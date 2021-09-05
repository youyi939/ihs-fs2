package com.hsgrjt.fushun.ihs.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: KenChen
 * @Description: 权限实体类
 * @Date: Create in  2021/9/4 下午3:00
 */
@Data
public class Permission {

    @ApiModelProperty("权限键")
    private String permissionKey;

    @ApiModelProperty("权限名称")
    private String permissionName;

    @ApiModelProperty("权限描述")
    private String description;

    @ApiModelProperty("权限类别")
    private String category;

    @ApiModelProperty("排序号")
    private Integer orderNum;
}
