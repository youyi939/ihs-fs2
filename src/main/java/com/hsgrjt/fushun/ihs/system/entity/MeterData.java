package com.hsgrjt.fushun.ihs.system.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 报表的最小数据单元
 * @Date: Create in  2021/11/20 下午7:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterData {

    private Integer id;

    @ApiModelProperty("格式化后的数据")
    private double meterData;

    @ApiModelProperty("格式化后的时间")
    private String meterTime;
}
