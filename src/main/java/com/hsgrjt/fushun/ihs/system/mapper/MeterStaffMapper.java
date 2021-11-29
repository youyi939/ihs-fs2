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

    @Select("SELECT week_num from week_form GROUP BY week_num")
    List<Integer> selectWeekNum();

}
