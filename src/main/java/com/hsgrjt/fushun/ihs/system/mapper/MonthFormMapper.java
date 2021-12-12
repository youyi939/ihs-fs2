package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import com.hsgrjt.fushun.ihs.system.entity.MonthForm;
import com.hsgrjt.fushun.ihs.system.entity.SelectTime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 月报表mapper
 * @Date: Create in  2021/11/30 下午3:21
 */
@Mapper
public interface MonthFormMapper extends BaseMapper<MonthForm> {

    @Select("select start_time,stop_time from month_form  where delete_flag = 0 GROUP BY start_time,stop_time")
    List<SelectTime> selectTime();

    @Select("SELECT * from month_form where \n" +
            "YEAR(start_time) = #{startYear} and MONTH(start_time) = #{startMonth} AND DAY(start_time) = #{startDay} \n" +
            "and\n" +
            "YEAR(stop_time) = #{stopYear} and MONTH(stop_time) = #{stopMonth} AND DAY(stop_time) = #{stopDay}\n" +
            "and machine_id = #{machineId} and delete_flag = 0")
    MonthForm selectMonthByTime(
            @Param("startYear")Integer startYear,
            @Param("startMonth")Integer startMonth,
            @Param("startDay")Integer startDay,
            @Param("stopYear")Integer stopYear,
            @Param("stopMonth")Integer stopMonth,
            @Param("stopDay")Integer stopDay,
            @Param("machineId")Long machineId
    );

}
