package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.MonthForm;
import com.hsgrjt.fushun.ihs.system.entity.SelectTime;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.SelectTimeDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/30 下午3:30
 */
@Service
public interface MonthService {

    /**
     * 生成月计划
     * @param dto 内部有起始日期和结束日期，第几周 月报表借用此dto
     * @param user 用户对象
     * @throws ParseException
     */
    void save(WeekPlanAddDTO dto, User user) throws ParseException;

    /**
     * 查询月报表的时间（分组）
     * @return
     */
    R<List<SelectTimeDTO>> selectTime();


    /**
     * 根据时间查询日报表对象
     * @param selectTime 时间查询对象
     * @return
     */
    R<List<MonthForm>> selectMonthFormByTime(SelectTime selectTime,User user);


    void deleteMonthForm(SelectTime selectTime);

}
