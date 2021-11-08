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

    private double totalArea;

    private double supplyArea;

    private double firstSuTempature;

    private double firstReTempature;

    private double firstSuPressure;

    private double firstRePressure;

    private double secSuTempature;

    private double secReTempature;

    private double secSuPressure;

    private double secRePressure;

    private double targetTempature;

    private double secPressureMax;

    private double secPressureSetMax;

    private double secPressureSetMin;

    private double secPressureMin;

    private double mainValveSet;

    private double mainValveFeed;

    private double auxValveSet;

    private double auxValveFeed;

    private double waterTankLevel;

    private double waterTankMinLevel;

    private Integer circulatingPumpOn;

    private double circulatingPumpSet;

    private double circulatingPumpFeed;

    private Integer circulatingPumpCode;

    private Integer replenishingPumpOn;

    private double replenishingPumpSet;

    private double replenishingPumpFeed;

    private Integer replenishingPumpCode;

    private double firstFlowSet;

    private double firstConsumption;

    private double firstFlow;

    private double firstFlowSum;

    private double firstInstantHeat;

    private double firstHeatTotal;

    private double firstHeatPredict;

    private double replenishingFlow;

    private double replenishingTotal;

    private double secFlow;

    private double secFlowSum;

    private double electricPower;

    private double electricTotal;

    private double heatConsumption;

    private double firstFlowStart;

    private double firstHeatStart;

    private double replenishingStart;

    private double electricStart;

    private double roomTempatureAvg;

    private Integer leaking;

    private Integer debug;

    private Date gmtCreate;


}
