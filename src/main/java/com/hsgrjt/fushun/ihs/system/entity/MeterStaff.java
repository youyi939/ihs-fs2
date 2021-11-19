package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 水电热对象
 * @Date: Create in  2021/11/19 下午2:31
 */


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="水电热表对象", description="")
public class MeterStaff {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("机组id")
    private Integer machineId;

    @ApiModelProperty("机组名字")
    private String machineName;

    @ApiModelProperty("热值")
    private double heat;

    @ApiModelProperty("水表数")
    private double water;

    @ApiModelProperty("电表数")
    private double power;

    @ApiModelProperty("数据插入时间")
    private Date gmtCreate;

}
