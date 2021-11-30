package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import org.springframework.stereotype.Service;

import java.text.ParseException;

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

}
