package com.hsgrjt.fushun.ihs.system.entity;

import com.hsgrjt.fushun.ihs.system.entity.dto.DayReportDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 日报表对象
 * @Date: Create in  2021/11/21 下午3:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayReport {

    @ApiModelProperty("中心站名字")
    private String centerStation;

    @ApiModelProperty("中心站下的机组数据")
    private List<DayReportDTO> dtoList;

    @ApiModelProperty("小记，即使当天所有换热站的数值数值加在一起，区分中心站")
    private List<ReportData> machineDataSum;

}
