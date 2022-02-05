package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.Goods;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import javax.crypto.spec.PSource;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/10/17 下午4:14
 */
@Mapper
public interface HeatNetworkDataMapper extends BaseMapper<HeatNetworkData> {

    @Select("SELECT id,station_id,total_area,supply_area,first_su_tempature,first_re_tempature,first_su_pressure,first_re_pressure,sec_su_tempature,sec_re_tempature,sec_su_pressure,sec_re_pressure,target_tempature,sec_pressure_max,sec_pressure_set_max,sec_pressure_set_min,sec_pressure_min,main_valve_set,main_valve_feed,aux_valve_set,aux_valve_feed,water_tank_level,water_tank_min_level,circulating_pump_on,circulating_pump_set,circulating_pump_feed,circulating_pump_code,replenishing_pump_on,replenishing_pump_set,replenishing_pump_feed,replenishing_pump_code,first_flow_set,first_consumption,first_flow,first_flow_sum,first_instant_heat,first_heat_total,first_heat_predict,replenishing_flow,replenishing_total,sec_flow,sec_flow_sum,electric_power,electric_total,heat_consumption,first_flow_start,first_heat_start,replenishing_start,electric_start,room_tempature_avg,leaking,debug,gmt_create FROM (SELECT * from heat_network_data WHERE station_id = #{id}  ) as total ORDER BY gmt_create DESC limit 1")
    HeatNetworkData selectData(@Param("id") Long id);


    /**
     * 根据时间，机组筛选折线图所需的数据
     * @param year 年
     * @param month 月
     * @param day 日
     * @param hour 小时（24小时制）
     * @param machineId 机组id
     * @param limitNum 截取的数据条数
     * @return
     */
    @Select("SELECT * from heat_network_data where " +
            "YEAR(gmt_create) = #{year} " +
            "and MONTH(gmt_create) = #{month} " +
            "AND DAY(gmt_create) = #{day} " +
            "AND HOUR(gmt_create) = #{hour}  " +
            "and station_id = #{machineId} " +
            "LIMIT #{limitNum}")
    List<HeatNetworkData> selectByTime(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("hour") Integer hour,
            @Param("machineId") Integer machineId,
            @Param("limitNum") Integer limitNum
    );


    /**
     * 根据时间，机组筛选折线图所需的数据
     * @param year 年
     * @param month 月
     * @param day 日
     * @param machineId 机组id
     * @return
     */
    @Select("SELECT * from heat_network_data where " +
            "YEAR(gmt_create) = #{year} " +
            "and MONTH(gmt_create) = #{month} " +
            "AND DAY(gmt_create) = #{day} " +
            "and station_id = #{machineId} ")
    List<HeatNetworkData> selectByTimeOneDay(
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("machineId") Integer machineId
    );





    @Update("update heat_network_data set main_valve_set = #{i}  where " +
            "YEAR(gmt_create) = #{year} " +
            "and MONTH(gmt_create) = #{month} " +
            "AND DAY(gmt_create) = #{day} " +
            "AND HOUR(gmt_create) = #{hour}  " +
            "and station_id = #{machineId} ")
    void updateByTime(
            @Param("i") double i,
            @Param("year") Integer year,
            @Param("month") Integer month,
            @Param("day") Integer day,
            @Param("hour") Integer hour,
            @Param("machineId") Integer machineId
    );

}
