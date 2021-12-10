package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 水电热表的mapper类
 * @Date: Create in  2021/11/19 下午2:38
 */
@Mapper
public interface MeterStaffMapper extends BaseMapper<MeterStaff> {


    @Update("update meter_staff set heat = #{data} where id = #{id};")
    void updateHeat(@Param("data")double data ,@Param("id") int id);

    @Update("update meter_staff set water = #{data} where id = #{id};")
    void updateWater(@Param("data")double data ,@Param("id") int id);

    @Update("update meter_staff set power = #{data} where id = #{id};")
    void updatePower(@Param("data")double data ,@Param("id") int id);

    @Select("SELECT * from meter_staff where YEAR(gmt_create) = #{year} and MONTH(gmt_create) = #{month} AND DAY(gmt_create) = #{day} and machine_id = #{machineId}")
    MeterStaff selectByTime(@Param("year") int year,@Param("month") int month,@Param("day") int day,@Param("machineId") Long machineId);

    @Update("update meter_staff set heat = #{heat} ,water = #{water} ,power = #{power} where machine_id = #{machineId} and\n" +
            "YEAR(gmt_create) = #{year}\n" +
            "and MONTH(gmt_create) = #{month}\n" +
            "AND DAY(gmt_create) = #{day};")
    void updateHeatByMachineId(
            @Param("machineId")Integer machineId
            ,@Param("water") double water
            ,@Param("power") double power
            ,@Param("heat") double heat
            ,@Param("year") Integer year
            ,@Param("month") Integer month
            ,@Param("day") Integer day
    );


    @Update("update meter_staff set power = #{power} where machine_id = #{machineId} and\n" +
            "YEAR(gmt_create) = #{year}\n" +
            "and MONTH(gmt_create) = #{month}\n" +
            "AND DAY(gmt_create) = #{day};")
    void updatePowerByMachineId(
            @Param("machineId")Integer machineId
            ,@Param("power") double power
            ,@Param("year") Integer year
            ,@Param("month") Integer month
            ,@Param("day") Integer day
    );

    @Update("update meter_staff set heat = #{heat} where machine_id = #{machineId} and\n" +
            "YEAR(gmt_create) = #{year}\n" +
            "and MONTH(gmt_create) = #{month}\n" +
            "AND DAY(gmt_create) = #{day};")
    void updateHeatByMachineId2(
            @Param("machineId")Integer machineId
            ,@Param("heat") double heat
            ,@Param("year") Integer year
            ,@Param("month") Integer month
            ,@Param("day") Integer day
    );

    @Update("update meter_staff set water = #{water} where machine_id = #{machineId} and\n" +
            "YEAR(gmt_create) = #{year}\n" +
            "and MONTH(gmt_create) = #{month}\n" +
            "AND DAY(gmt_create) = #{day};")
    void updateWaterByMachineId(
            @Param("machineId")Integer machineId
            ,@Param("water") double water
            ,@Param("year") Integer year
            ,@Param("month") Integer month
            ,@Param("day") Integer day
    );

}
