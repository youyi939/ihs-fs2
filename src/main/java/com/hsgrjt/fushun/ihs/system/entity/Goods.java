package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.hsgrjt.fushun.ihs.system.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 文件实体类
 * @Date: Create in  2021/8/21 下午5:42
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="物料表对象", description="")
public class Goods extends BaseEntity implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("物料名称")
    private String name;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("价格")
    private double price;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("描述")
    private String remark;

}
