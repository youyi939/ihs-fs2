package com.hsgrjt.fushun.ihs.system.service;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.Plan;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: KenChen
 * @Description: 年指标service
 * @Date: Create in  2021/11/24 下午2:00
 */
@Service
public interface PlanService {


    /**
     * 查询当前公司下的机组的计划数据
     * @param company
     * @return
     */
    R<List<Plan>> findAll(String company);

    /**
     * 更新指标
     * @param plan
     */
    void updateById(Plan plan);


    /**
     * 初始化年计划表所有数据，方法留存。(想调用的话清表之后调用，会生成所有公司的所有机组的年计划对象，初始数据为0)
     */
    void initPlan();

    /**
     * 初始化计划数据
     * @param company 公司名
     */
    void initData(String company);

    /**
     * 查询机组的计划对象
     * @param stationName 机组名称
     * @return 计划对象
     */
    Plan selectByStationName(String stationName);

    Plan add(HeatMachine heatMachine);

}
