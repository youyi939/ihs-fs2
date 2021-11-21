package com.hsgrjt.fushun.ihs.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: KenChen
 * @Description: 报表的奇奇怪怪的数据，什么小记什么的，规律就是当天所有机组加在一起的数据
 * @Date: Create in  2021/11/20 下午7:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportData {

    @ApiModelProperty("小记")
    private double samllData;

    @ApiModelProperty("结余")
    private double residue;

    @ApiModelProperty("格式化后的时间")
    private String time;
}
