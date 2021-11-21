package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.hsgrjt.fushun.ihs.system.entity.MeterData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 日报表的下层对象
 * @Date: Create in  2021/11/20 下午7:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayReportDTO{

    @ApiModelProperty("机组名字")
    private String stationName;

    @ApiModelProperty("该中心站下的机组的水电热数据")
    private List<MeterData> meterDataList;

    @ApiModelProperty("合计,也就是当前机组的所有日期的数")
    private double bigSum;

}
