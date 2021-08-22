package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hsgrjt.fushun.ihs.system.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 文件实体类
 * @Date: Create in  2021/8/21 下午5:42
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="文件表对象", description="")
public class IhsFile extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文件名")
    private String name;

    @ApiModelProperty("下载地址")
    private String url;

    @ApiModelProperty("创建用户ID")
    private int createUserId;

    @ApiModelProperty("文件类别")
    private String category;

    @ApiModelProperty("可访问的公司（逗号分隔，空表示都可以访问）")
    private String allowCompanys;

    @ApiModelProperty("可访问的服务部（逗号分隔，空表示都可以访问）")
    private String allowServiceDeps;

    @ApiModelProperty("可访问的中心站（逗号分隔，空表示都可以访问）")
    private String allowCenterStations;

    @ApiModelProperty("共享给那些用户ID")
    private String shareToUsers;

    @ApiModelProperty("数据插入时间")
    private Date gmtCreate;

    @ApiModelProperty("最后修改时间")
    private Date gmtModified;


}
