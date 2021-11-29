package com.hsgrjt.fushun.ihs.system.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/27 下午1:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeekPlanAddDTO {

    @ApiModelProperty("第几周")
    private Integer weekNum;

    @ApiModelProperty("起始日期")
    private String startTime;

    @ApiModelProperty("截止日期")
    private String stopTime;
    
}
