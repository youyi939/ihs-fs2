package com.hsgrjt.fushun.ihs.system.mapper;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-11
 */
@Mapper
public interface HeatMachineMapper extends BaseMapper<HeatMachine> {

    @Select("select distinct center_station from heat_machine where company = #{company};")
    List<String> getCenterStation(@Param("company") String company);

}
