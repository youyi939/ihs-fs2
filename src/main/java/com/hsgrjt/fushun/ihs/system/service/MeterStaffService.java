package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.dto.DayFormDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterUpdateDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 水电热表的service类
 * @Date: Create in  2021/11/19 下午2:40
 */
@Service
public interface MeterStaffService {

    /**
     * 新建
     * @param dto
     */
    void save(MeterStaffAddDTO dto);


    /**
     * 查询当月的水电热报表原始数据
     * @return
     */
    R<List<MeterDataDTO>> findAll(User user,String type,Integer selectYear,Integer selectMonth);

    /**
     * 查看今天的日报表
     * @param user
     * @return
     */
    R<List<DayFormDTO>> getDayFromWater(User user,Integer selectYear,Integer selectMonth,String type);


    void initDataEveryDay();


    /**
     * 更新水电热数据
     * @param dto
     */
    void update(MeterUpdateDTO dto);


    /**
     * 每天抄表的接口，只更新当天的数据，不新增
     * @param machineId 机组id
     * @param water 水
     * @param power 电
     * @param heat 热
     * @return
     */
    R updateByMachineId(Integer machineId,double water,double power,double heat);


}
