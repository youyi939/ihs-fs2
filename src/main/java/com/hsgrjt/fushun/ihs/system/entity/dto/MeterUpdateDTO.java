package com.hsgrjt.fushun.ihs.system.entity.dto;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KenChen
 * @Description: 水电热数据更新
 * @Date: Create in  2021/11/25 下午3:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterUpdateDTO {

    @ApiModelProperty("水电热的id")
    private Integer id;

    @ApiModelProperty("机组名称")
    private String stationName;

    @ApiModelProperty("更新数值")
    private double updateData;

    @ApiModelProperty("水电热")
    private String type;

}
