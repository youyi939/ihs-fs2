package com.hsgrjt.fushun.ihs.system.entity.dto;

import com.hsgrjt.fushun.ihs.system.entity.MeterData;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 报表原始数据封装单元
 * @Date: Create in  2021/11/20 下午7:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeterDataDTO {

    @ApiModelProperty("中心站名字")
    private String stationName;

    @ApiModelProperty("该中心站下的机组的水电热数据")
    private List<MeterData> meterDataList;

}
