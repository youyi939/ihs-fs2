package com.hsgrjt.fushun.ihs.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/8/22 下午5:26
 */

@Data
public class IhsFileAddDTO {

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("下载地址")
    private String url;

    @ApiModelProperty("创建用户ID")
    private int createUserId;

    @ApiModelProperty("文件类别")
    private String category;

    @ApiModelProperty("共享给那些用户ID")
    private String shareToUsers;

    @ApiModelProperty("可访问的公司（逗号分隔，空表示都可以访问）")
    private String allowCompanys;;
}
