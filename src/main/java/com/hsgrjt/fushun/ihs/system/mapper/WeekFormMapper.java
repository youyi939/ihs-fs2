package com.hsgrjt.fushun.ihs.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hsgrjt.fushun.ihs.system.entity.Plan;
import com.hsgrjt.fushun.ihs.system.entity.WeekForm;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/27 上午12:29
 */
@Mapper
public interface WeekFormMapper extends BaseMapper<WeekForm> {

    /**
     * 根据年限和周数查询周报表对象list
     * @param weekNum
     * @param year
     * @return
     */
    @Select("SELECT * from week_form where YEAR(start_time) = #{year} and week_num = #{weekNum}")
    List<WeekForm> selectByWeekNum(@Param("weekNum")Integer weekNum,@Param("year") String year);

}
