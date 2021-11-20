package com.hsgrjt.fushun.ihs.system.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/20 下午1:39
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="水电热表AddDTO对象", description="")
public class MeterStaffAddDTO {

    @ApiModelProperty("机组id")
    private Integer machineId;

    @ApiModelProperty("热值")
    private double heat;

    @ApiModelProperty("水表数")
    private double water;

    @ApiModelProperty("电表数")
    private double power;
}
