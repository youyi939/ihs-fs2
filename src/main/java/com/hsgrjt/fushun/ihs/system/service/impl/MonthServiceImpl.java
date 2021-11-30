package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.*;
import com.hsgrjt.fushun.ihs.system.entity.dto.SelectTimeDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.mapper.MonthFormMapper;
import com.hsgrjt.fushun.ihs.system.mapper.WeekFormMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MonthService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hsgrjt.fushun.ihs.system.service.impl.WeekFormServiceImpl.getDaysOfMonth;

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


    @Override
    public void save(WeekPlanAddDTO dto, User user) throws ParseException {
        //计算日指标
        Map<Integer, Double> monthScale = new HashMap<Integer, Double>();
        monthScale.put(11, 0.17);
        monthScale.put(12, 0.25);
        monthScale.put(1, 0.25);
        monthScale.put(2, 0.19);
        monthScale.put(3, 0.14);
        //准备初始数据
        String start = dto.getStartTime();
        String stop = dto.getStopTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //起始日期
        Date startTime = sdf.parse(start);
        //结束日期
        Date stopTime = sdf.parse(stop);
        //第几周
        Integer weekNum = dto.getWeekNum();

        String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(startTime).toString().split("-");
        String[] strNow2 = new SimpleDateFormat("yyyy-MM-dd").format(stopTime).toString().split("-");

        //获取年月日信息
        int startYear = Integer.parseInt(strNow1[0]);
        int startMonth = Integer.parseInt(strNow1[1]);
        int startDay = Integer.parseInt(strNow1[2]);

        int stopYear = Integer.parseInt(strNow2[0]);
        int stopMonth = Integer.parseInt(strNow2[1]);
        int stopDay = Integer.parseInt(strNow2[2]);

        long from1 = startTime.getTime();
        long to1 = stopTime.getTime();
        //日期差
        int diffDays = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));

        int startMaxDay = getDaysOfMonth(startTime);
        int stopMaxDay = getDaysOfMonth(stopTime);

        //获取当前的机组列表
        List<HeatMachine> machineList = machineService.getMachineByUser(user);
        List<MonthForm> monthFormList = new ArrayList<>();

        for (int i = 0; i < machineList.size(); i++) {
            Plan plan = planService.selectByStationName(machineList.get(i).getName());
            MonthForm monthForm = new MonthForm();
            //设置中心站
            monthForm.setCenterStation(machineList.get(i).getCenterStation());

            monthForm.setCreateYear("200*");

            //开阀面积
            monthForm.setArea((int)plan.getArea());

            //户数 - 开阀率
            monthForm.setResidentNum(0);
            monthForm.setOpenValve("0%");

            //设置所占比例
            double proportion = monthScale.get(startMonth)/getDaysOfMonth(startTime)*diffDays;
            NumberFormat format = NumberFormat.getPercentInstance();
            //小数最大保留2位
            format.setMaximumFractionDigits(2);
            String stay = format.format(proportion);
            monthForm.setProportion(stay);

            //计划指标 - 水电热
            monthForm.setPlanTargetWater(monthForm.getArea()*plan.getWaterPlan()*proportion/1000);
            monthForm.setPlanTargetPower(monthForm.getArea()*plan.getPowerPlan()*proportion/1000);
            monthForm.setPlanTargetHeat(monthForm.getArea()*proportion/plan.getHeatPlan());


            //实耗量 - 水电热
            MeterStaff startMeter = meterStaffMapper.selectByTime(startYear, startMonth, startDay, machineList.get(i).getId());
            MeterStaff stopMeter = meterStaffMapper.selectByTime(stopYear, stopMonth, stopDay, machineList.get(i).getId());
            if (V.isEmpty(startMeter) || V.isEmpty(stopMeter)) {
                monthForm.setExpendWater(0);
                monthForm.setExpendPower(0);
                monthForm.setExpendHeat(0);
            }else {
                monthForm.setExpendWater(stopMeter.getWater() - startMeter.getWater());
                monthForm.setExpendPower(stopMeter.getPower() - startMeter.getPower());
                monthForm.setExpendHeat(stopMeter.getHeat() - startMeter.getHeat());
            }

            //当月结余 - 水电热 计划减去实耗
            monthForm.setResidueWater(monthForm.getPlanTargetWater() - monthForm.getExpendWater());
            monthForm.setResiduePower(monthForm.getPlanTargetPower() - monthForm.getExpendPower());
            monthForm.setResidueHeat(monthForm.getPlanTargetHeat() - monthForm.getExpendHeat());

            //指标 - 水电热
            monthForm.setTargetWater(plan.getWaterPlan());
            monthForm.setTargetPower(plan.getPowerPlan());
            monthForm.setTargetHeat(plan.getHeatPlan());


            //单耗
            if (monthForm.getExpendWater() == 0){
                monthForm.setUnitWater(0);
            }else {
                monthForm.setUnitWater(monthForm.getExpendWater()/monthForm.getArea()/proportion*1000);
            }

            if (monthForm.getExpendPower() == 0){
                monthForm.setUnitPower(0);
            }else {
                monthForm.setUnitPower(monthForm.getExpendPower()/monthForm.getArea()/proportion*1000);
            }

            if (monthForm.getExpendHeat() == 0){
                monthForm.setUnitHeat(0);
            }else {
                monthForm.setUnitHeat(monthForm.getExpendHeat()/monthForm.getArea()/proportion*1000);
            }

            monthForm.setMachineName(machineList.get(i).getName());
            monthForm.setMachineId(Math.toIntExact(machineList.get(i).getId()));
            monthForm.setStartTime(startTime);
            monthForm.setStopTime(stopTime);
            monthForm.setDeleteFlag(false);
            monthFormList.add(monthForm);
        }

        for (MonthForm monthForm : monthFormList) {
            monthFormMapper.insert(monthForm);
        }
    }

    @Override
    public R<List<SelectTimeDTO>> selectTime() {
        List<SelectTimeDTO> dtoList = new ArrayList<>();
        List<SelectTime> selectTimeList = monthFormMapper.selectTime();
        for (SelectTime selectTime : selectTimeList) {
            SelectTimeDTO dto = new SelectTimeDTO();
            dto.setSelectTime(selectTime);
            String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(selectTime.getStartTime()).toString().split("-");
            String[] strNow2 = new SimpleDateFormat("yyyy-MM-dd").format(selectTime.getStopTime()).toString().split("-");

            //获取年月日信息
            int startYear = Integer.parseInt(strNow1[0]);
            int startMonth = Integer.parseInt(strNow1[1]);
            int startDay = Integer.parseInt(strNow1[2]);

            int stopYear = Integer.parseInt(strNow2[0]);
            int stopMonth = Integer.parseInt(strNow2[1]);
            int stopDay = Integer.parseInt(strNow2[2]);
            dto.setMsg(startYear+"-"+startMonth+"-"+startDay+"/"+stopYear+"-"+stopMonth+"-"+stopDay);
            dtoList.add(dto);
        }
        return R.ok("查询成功").putData(dtoList);
    }

    @Override
    public R<List<MonthForm>> selectMonthFormByTime(SelectTime selectTime,User user) {
        List<HeatMachine> machineList = machineService.getMachineByUser(user);

        List<MonthForm> monthFormList = new ArrayList<>();
        String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(selectTime.getStartTime()).toString().split("-");
        String[] strNow2 = new SimpleDateFormat("yyyy-MM-dd").format(selectTime.getStopTime()).toString().split("-");

        //获取年月日信息
        int startYear = Integer.parseInt(strNow1[0]);
        int startMonth = Integer.parseInt(strNow1[1]);
        int startDay = Integer.parseInt(strNow1[2]);

        int stopYear = Integer.parseInt(strNow2[0]);
        int stopMonth = Integer.parseInt(strNow2[1]);
        int stopDay = Integer.parseInt(strNow2[2]);

        for (HeatMachine heatMachine : machineList) {
            monthFormList.add(monthFormMapper.selectMonthByTime(startYear,startMonth,startDay,stopYear,stopMonth,stopDay,heatMachine.getId()));
        }

        return R.ok("查询成功").putData(monthFormList);
    }

    @Override
    public void deleteMonthForm(SelectTime selectTime) {
        QueryWrapper<MonthForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MonthForm::getStartTime,selectTime.getStartTime()).eq(MonthForm::getStopTime,selectTime.getStopTime());
        monthFormMapper.delete(queryWrapper);
    }


}
