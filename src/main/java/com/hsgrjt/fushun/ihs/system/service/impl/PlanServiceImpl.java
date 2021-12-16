package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.Plan;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.PlanMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/24 下午2:00
 */
@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanMapper mapper;

    @Autowired
    UserService userService;

    @Autowired
    HeatMachineService machineService;


    @SneakyThrows
    @Override
    public R<List<Plan>> findAll(String company) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Plan::getCompany,company);
        List<Plan> planList = mapper.selectList(queryWrapper);
        return R.ok("查询成功").putData(planList);
    }

    @Override
    public void updateById(Plan plan) {

        //热有两个，这个取倒数
        double data = plan.getHeatPlan();
        plan.setHeatPlan2(1/data);
        plan.setUpdateTime(new Date());
        mapper.updateById(plan);
    }

    @Override
    public void initPlan() {
        initData("城南热电");
        initData("抚顺新北方");
        initData("新北方高湾");
    }

    @Override
    public Plan selectByStationName(String stationName) {
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Plan::getStationName,stationName);
        return mapper.selectOne(queryWrapper);
    }

    @Override
    public Plan add(HeatMachine heatMachine) {
        return null;
    }

    @Override
    public void initData(String company){
        User user1 = new User();
        user1.setAllowCompanys(company);
        List<HeatMachine> machineList1 = machineService.getMachineByUser(user1);
        for (HeatMachine heatMachine : machineList1) {
            Plan plan = new Plan();
            plan.setCompany(company);
            plan.setStationName(heatMachine.getName());
            plan.setUpdateTime(new Date());
            plan.setStationId(heatMachine.getId().intValue());
            mapper.insert(plan);
        }
    }


}
