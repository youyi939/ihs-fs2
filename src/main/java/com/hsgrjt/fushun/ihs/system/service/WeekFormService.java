package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.WeekForm;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/27 上午12:30
 */
@Service
public interface WeekFormService {

    /**
     * 生成周计划
     * @param dto 内部有起始日期和结束日期，第几周
     * @param user 用户对象
     * @param type 水电热
     * @throws ParseException
     */
    void save(WeekPlanAddDTO dto, User user,String type) throws ParseException;

    /**
     * 根据年份和第几周查询周报表对象
     * @param weekNum 周数
     * @param year 年限
     */
    R<List<WeekForm>> selectByWeekNum(Integer weekNum, String year);


    /**
     * 查询数据库中的周数集合
     * @return
     */
    R<List<Integer>> selectWeekNum();

    /**
     * 删除周报表对象
     * @param weekNum
     */
    void deleteWeekForm(Integer weekNum);


    /**
     * 查询周报表对象
     * @param weekNum
     * @param type
     * @return
     */
    R<List<WeekForm>> selectWeekForm(Integer weekNum,String type);

}
