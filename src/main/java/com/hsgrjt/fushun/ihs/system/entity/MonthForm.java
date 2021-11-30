package com.hsgrjt.fushun.ihs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hsgrjt.fushun.ihs.system.entity.vo.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: KenChen
 * @Description: 月报表对象
 * @Date: Create in  2021/11/30 下午2:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthForm extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("中心站名字")
    private String centerStation;

    @ApiModelProperty("机组id")
    private Integer machineId;

    @ApiModelProperty("机组名称")
    private String machineName;

    @ApiModelProperty("建设年份")
    private String createYear;

    @ApiModelProperty("面积")
    private Integer area;

    @ApiModelProperty("户数")
    private Integer residentNum;

    @ApiModelProperty("开阀率")
    private String openValve;

    @ApiModelProperty("所占比例")
    private String proportion;

    @ApiModelProperty("计划指标水")
    private double planTargetWater;

    @ApiModelProperty("实耗量水")
    private double expendWater;

    @ApiModelProperty("当月结余-水")
    private double residueWater;

    @ApiModelProperty("指标-水")
    private double targetWater;

    @ApiModelProperty("单耗-水")
    private double unitWater;

    @ApiModelProperty("计划指标电")
    private double planTargetPower;

    @ApiModelProperty("实耗量电")
    private double expendPower;

    @ApiModelProperty("当月结余-电")
    private double residuePower;

    @ApiModelProperty("指标-电")
    private double targetPower;

    @ApiModelProperty("单耗-电")
    private double unitPower;

    @ApiModelProperty("计划指标热")
    private double planTargetHeat;

    @ApiModelProperty("实耗量热")
    private double expendHeat;

    @ApiModelProperty("当月结余-热")
    private double residueHeat;

    @ApiModelProperty("指标-热")
    private double targetHeat;

    @ApiModelProperty("单耗-热")
    private double unitHeat;

    @ApiModelProperty("起始日期")
    private Date startTime;

    @ApiModelProperty("结束日期")
    private Date stopTime;

}
