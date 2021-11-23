package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.hsgrjt.fushun.ihs.system.entity.MeterData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 日报表通用返回对象
 * @Date: Create in  2021/11/23 下午3:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayFormDTO {

    @ApiModelProperty("中心站名字")
    private String centerStation;

    @ApiModelProperty("机组名字")
    private String stationName;

    @ApiModelProperty("合计,也就是当前机组的所有日期的数")
    private double bigSum;

    private List<MeterData> meterDataList;

    @ApiModelProperty("小记")
    private double samllData;

    @ApiModelProperty("结余")
    private double residue;


}
