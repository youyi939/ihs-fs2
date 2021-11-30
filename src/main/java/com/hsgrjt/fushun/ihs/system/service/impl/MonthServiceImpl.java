package com.hsgrjt.fushun.ihs.system.service.impl;

import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.mapper.MonthFormMapper;
import com.hsgrjt.fushun.ihs.system.mapper.WeekFormMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MonthService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/30 下午3:30
 */
@Service
public class MonthServiceImpl implements MonthService {

    @Autowired
    MonthFormMapper monthFormMapper;

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @Autowired
    UserService userService;

    @Autowired
    HeatMachineService machineService;

    @Autowired
    PlanService planService;


}
