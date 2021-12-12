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
 * @Description:
 * @Date: Create in  2021/10/17 下午4:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatNetworkData {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("换热站id")
    private Integer stationId;

    @ApiModelProperty("联网面积")
    private double totalArea;

    @ApiModelProperty("实供面积")
    private double supplyArea;

    @ApiModelProperty("一网供温")
    private double firstSuTempature;

    @ApiModelProperty("一网回温")
    private double firstReTempature;

    @ApiModelProperty("一网供压")
    private double firstSuPressure;

    @ApiModelProperty("一网回压")
    private double firstRePressure;

    @ApiModelProperty("二网供温")
    private double secSuTempature;

    @ApiModelProperty("二网回温")
    private double secReTempature;

    @ApiModelProperty("二网供压")
    private double secSuPressure;

    @ApiModelProperty("二网回压")
    private double secRePressure;

    @ApiModelProperty("温度目标设定")
    private double targetTempature;

    @ApiModelProperty("二网压力上限")
    private double secPressureMax;

    @ApiModelProperty("二网压力设定上限")
    private double secPressureSetMax;

    @ApiModelProperty("二网压力设定下限")
    private double secPressureSetMin;

    @ApiModelProperty("二网压力下限")
    private double secPressureMin;

    @ApiModelProperty("一阀给定")
    private double mainValveSet;

    @ApiModelProperty("一阀反馈")
    private double mainValveFeed;

    @ApiModelProperty("二阀给定")
    private double auxValveSet;

    @ApiModelProperty("二阀反馈")
    private double auxValveFeed;

    @ApiModelProperty("水箱水位")
    private double waterTankLevel;

    @ApiModelProperty("水箱水位下限")
    private double waterTankMinLevel;

    @ApiModelProperty("循环泵启动")
    private Integer circulatingPumpOn;

    @ApiModelProperty("循环泵给定")
    private double circulatingPumpSet;

    @ApiModelProperty("循环泵频率")
    private double circulatingPumpFeed;

    @ApiModelProperty("循环泵故障代码")
    private Integer circulatingPumpCode;

    @ApiModelProperty("补水泵启动")
    private Integer replenishingPumpOn;

    @ApiModelProperty("补水泵给定")
    private double replenishingPumpSet;

    @ApiModelProperty("补水泵频率")
    private double replenishingPumpFeed;

    @ApiModelProperty("补水泵故障代码")
    private Integer replenishingPumpCode;

    @ApiModelProperty("一网流量设定")
    private double firstFlowSet;

    @ApiModelProperty("一网流量单耗")
    private double firstConsumption;

    @ApiModelProperty("一网流量")
    private double firstFlow;

    @ApiModelProperty("一网流量累计")
    private double firstFlowSum;

    @ApiModelProperty("一网热量")
    private double firstInstantHeat;

    @ApiModelProperty("一网热量累计")
    private double firstHeatTotal;

    @ApiModelProperty("预测热量")
    private double firstHeatPredict;

    @ApiModelProperty("补水流量")
    private double replenishingFlow;

    @ApiModelProperty("补水流量累计")
    private double replenishingTotal;

    @ApiModelProperty("二网流量")
    private double secFlow;

    @ApiModelProperty("二网流量累计")
    private double secFlowSum;

    @ApiModelProperty("瞬时电量")
    private double electricPower;

    @ApiModelProperty("电量累计")
    private double electricTotal;

    @ApiModelProperty("热负荷")
    private double heatConsumption;

    @ApiModelProperty("一网流量、年初始")
    private double firstFlowStart;

    @ApiModelProperty("一网热量、年初始")
    private double firstHeatStart;

    @ApiModelProperty("补水流量、年初始")
    private double replenishingStart;

    @ApiModelProperty("电量、年初始")
    private double electricStart;

    @ApiModelProperty("室外气温")
    private double roomTempatureAvg;

    @ApiModelProperty("地面积水报警")
    private Integer leaking;

    @ApiModelProperty("调试状态开启")
    private Integer debug;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("数据时间")
    private Date gmtCreate;


}
