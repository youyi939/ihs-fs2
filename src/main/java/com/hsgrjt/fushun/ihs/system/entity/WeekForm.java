package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 周报表对象
 * @Date: Create in  2021/11/27 上午12:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekForm {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("机组名称")
    private String machineName;

    @ApiModelProperty("机组id")
    private Integer machineId;

    @ApiModelProperty("中心站名字")
    private String centerStation;

    @ApiModelProperty("开阀面积")
    private double area;

    @ApiModelProperty("入住率")
    private double check;

    @ApiModelProperty("本周比例")
    private double thisWeekRatio;

    @ApiModelProperty("本周计划指标")
    private double thisWeekPlan;

    @ApiModelProperty("上周实耗量")
    private double lastWeekExpend;

    @ApiModelProperty("上周实耗量")
    private double thisWeekExpend;

    @ApiModelProperty("本周单耗")
    private double thisWeekUnit;

    @ApiModelProperty("去年本周单耗")
    private double lastYearThisWeekUnit;

    @ApiModelProperty("累计计划指标")
    private double sumPlan;

    @ApiModelProperty("累计实耗量")
    private double sumExpend;

    @ApiModelProperty("累计单耗")
    private double sumUnit;

    @ApiModelProperty("本周结余")
    private double thisWeekResidue;

    @ApiModelProperty("累计结余")
    private double sumResidue;

    @ApiModelProperty("年度给定")
    private double yearPlan;

    @ApiModelProperty("起始日期")
    private Date startTime;

    @ApiModelProperty("结束日期")
    private Date stopTime;

    @ApiModelProperty("第几周")
    private Integer weekNum;

    @ApiModelProperty("删除")
    private double deleteFlag;

    @ApiModelProperty("公司名称")
    private String company;

}
