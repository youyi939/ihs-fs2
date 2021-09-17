package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hsgrjt.fushun.ihs.system.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: KenChen
 * @Description: 责任成本
 * @Date: Create in  2021/9/17 下午11:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="责任成本表对象", description="")
public class Cost extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("个数")
    private Integer num;

    @ApiModelProperty("描述")
    private String remark;

}
