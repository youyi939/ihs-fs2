package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 年指标对象
 * @Date: Create in  2021/11/24 下午1:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("机组名称")
    private String stationName;

    @ApiModelProperty("公司名称")
    private String company;

    @ApiModelProperty("年指标-水")
    private double waterPlan;

    @ApiModelProperty("年指标-热")
    private double heatPlan;

    @ApiModelProperty("年指标-热")
    private double heatPlan2;

    @ApiModelProperty("年指标-电")
    private double powerPlan;

    @ApiModelProperty("实际供热面积")
    private double area;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("更新时间")
    private Date updateTime;

}
