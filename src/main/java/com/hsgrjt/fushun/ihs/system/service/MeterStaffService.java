package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.DayReport;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
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
    R<List<MeterDataDTO>> findAll(User user,String type);

    /**
     * 查看今天的日报表
     * @param user
     * @return
     */
    R<List<DayReport>> getDayFromWater(User user);


    void initDataEveryDay();
}
