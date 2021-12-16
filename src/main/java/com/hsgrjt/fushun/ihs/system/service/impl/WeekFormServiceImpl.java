package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.*;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.mapper.WeekFormMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.system.service.WeekFormService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/27 上午12:31
 */
@Service
public class WeekFormServiceImpl implements WeekFormService {

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @Autowired
    UserService userService;

    @Autowired
    HeatMachineService machineService;

    @Autowired
    PlanService planService;

    @Autowired
    WeekFormMapper mapper;


    /**
     * 目前是新增水
     *
     * @param dto
     * @param user
     * @param type
     * @throws ParseException
     */
    @Override
    public void save(WeekPlanAddDTO dto, User user, String type) throws ParseException {
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
        int diffDays = (int) ((to1 - from1) / (1000 * 60 * 60 * 24)) + 1;

        int startMaxDay = getDaysOfMonth(startTime);
        int stopMaxDay = getDaysOfMonth(stopTime);

        //获取当前的机组列表
        List<HeatMachine> machineList = machineService.getMachineByUser(user);
        List<WeekForm> weekFormList = new ArrayList<>();

        for (int i = 0; i < machineList.size(); i++) {
            Plan plan = planService.selectByStationName(machineList.get(i).getName(),machineList.get(i).getId().intValue());
            WeekForm weekForm = new WeekForm();
            //设置机组名
            weekForm.setCompany(machineList.get(i).getCompany());
            //设置中心站
            weekForm.setCenterStation(machineList.get(i).getCenterStation());

            //开阀面积
            weekForm.setArea(plan.getArea());

            NumberFormat format = NumberFormat.getPercentInstance();
            //小数最大保留2位
            format.setMaximumFractionDigits(2);
            String stay = format.format(plan.getStay());
            //设置入住率
            weekForm.setStay(stay);

            //计算本周比例 区分跨月和不跨月两种情况
            if (startMonth == stopMonth) {
                double d = parseData3((monthScale.get(startMonth) / startMaxDay) * diffDays);
                weekForm.setThisWeekRatio(d);
            } else {
                int maxStart = getDaysOfMonth(startTime);
                //包含的第一个月的天数
                int startMonthDay = maxStart - startDay;

                int maxStop = getDaysOfMonth(stopTime);
                //包含的第二个月的天数
                double d = parseData3(((monthScale.get(startMonth) / maxStart)) * startMonthDay + ((monthScale.get(stopMonth) / maxStop)) * stopDay);
                weekForm.setThisWeekRatio(d);
            }

            //本周计划指标

            double thisWeekPlan = 0;
            switch (type) {
                case "水":
                    thisWeekPlan = parseData2(plan.getWaterPlan() * plan.getArea() * weekForm.getThisWeekRatio() / 1000);
                    weekForm.setThisWeekPlan(thisWeekPlan);
                    break;
                case "电":
                    thisWeekPlan = parseData2(plan.getPowerPlan() * plan.getArea() * weekForm.getThisWeekRatio());
                    weekForm.setThisWeekPlan(thisWeekPlan);
                    break;
                case "热":
                    thisWeekPlan = parseData2(1 / plan.getHeatPlan() * plan.getArea() * weekForm.getThisWeekRatio());
                    weekForm.setThisWeekPlan(thisWeekPlan);
                    break;
                default:
                    break;
            }


            //本周实耗
            MeterStaff startMeter = meterStaffMapper.selectByTime(startYear, startMonth, startDay, machineList.get(i).getId());
            MeterStaff stopMeter = meterStaffMapper.selectByTime(stopYear, stopMonth, stopDay + 1, machineList.get(i).getId());
            if (V.isEmpty(startMeter) || V.isEmpty(stopMeter)) {
                weekForm.setThisWeekExpend(0);
            } else {
                switch (type) {
                    case "水":
                        weekForm.setThisWeekExpend(stopMeter.getWater() - startMeter.getWater());
                        break;
                    case "电":
                        weekForm.setThisWeekExpend(stopMeter.getPower() * machineList.get(i).getMultiplyingPower() - startMeter.getPower() * machineList.get(i).getMultiplyingPower());
                        break;
                    case "热":
                        weekForm.setThisWeekExpend(stopMeter.getHeat() - startMeter.getHeat());
                        break;
                    default:
                        break;
                }
            }

            //上周实耗，第一周的数据都为0，第二周开始本数据为上周的本周实耗
            //累计计划指标 -第一周等于本周计划指标，第二周等于本周计划指标+上一周计划指标
            //累计实耗
            if (weekNum == 1) {
                weekForm.setLastWeekExpend(0);
                weekForm.setSumPlan(weekForm.getThisWeekPlan());
                weekForm.setSumExpend(weekForm.getThisWeekExpend());
            } else {
                QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(WeekForm::getCompany, user.getAllowCompanys()).eq(WeekForm::getMachineId, machineList.get(i).getId()).eq(WeekForm::getWeekNum, weekNum - 1).eq(WeekForm::getType, type);
                WeekForm lastWeekForm = mapper.selectOne(queryWrapper);
                weekForm.setLastWeekExpend(lastWeekForm.getThisWeekExpend());
                weekForm.setSumPlan(lastWeekForm.getThisWeekPlan() + weekForm.getThisWeekPlan());
                weekForm.setSumExpend(lastWeekForm.getThisWeekExpend() + weekForm.getThisWeekExpend());
            }

            //本周结余
            weekForm.setThisWeekResidue(weekForm.getThisWeekPlan() - weekForm.getThisWeekExpend());

            //本周单耗-去年本周单耗
            if (weekForm.getArea() == 0 || weekForm.getThisWeekExpend() == 0 || weekForm.getThisWeekRatio() == 0) {
                weekForm.setThisWeekUnit(0);
            } else {
                double d = 0;
                switch (type) {
                    case "水":
                        d = (weekForm.getThisWeekExpend() / weekForm.getArea() / weekForm.getThisWeekRatio()) * 1000;
                        d = parseData2(d);
                        weekForm.setThisWeekUnit(d);
                        break;
                    case "电":
                        d = (weekForm.getThisWeekExpend() / weekForm.getArea() / weekForm.getThisWeekRatio());
                        d = parseData2(d);
                        weekForm.setThisWeekUnit(d);
                        break;
                    case "热":
                        d = (weekForm.getThisWeekExpend() / weekForm.getArea() / weekForm.getThisWeekRatio());
                        d = parseData2(1 / d);
                        weekForm.setThisWeekUnit(d);
                        break;
                    default:
                        break;
                }

            }
            weekForm.setLastYearThisWeekUnit(0);


            //计算累计单耗
            //开始和结束日期都是11月份，证明没有跨月的情况
            double n = 0;
            if (startMonth == stopMonth) {

                //0/0==NAN没法计算
                if (weekForm.getArea() == 0.0) {
                    weekForm.setSumUnit(0);
                } else {
                    switch (type) {
                        case "水":
                            n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) / startMaxDay * stopDay) * 1000;
                            weekForm.setSumUnit(parseData2(n));
                            break;
                        case "电":
                            n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) / startMaxDay * stopDay);
                            weekForm.setSumUnit(parseData2(n));
                            break;
                        case "热":
                            n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) / startMaxDay * stopDay);
                            weekForm.setSumUnit(parseData2(1/n));
                            break;
                        default:
                            break;
                    }
                }
            } else {
                switch (type) {
                    case "水":
                        n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) + monthScale.get(stopMonth) / stopMaxDay * stopDay) * 1000;
                        weekForm.setSumUnit(parseData2(n));
                        break;
                    case "电":
                        n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) + monthScale.get(stopMonth) / stopMaxDay * stopDay);
                        weekForm.setSumUnit(parseData2(n));
                        break;
                    case "热":
                        n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) + monthScale.get(stopMonth) / stopMaxDay * stopDay);
                        weekForm.setSumUnit(parseData2(1/n));
                        break;
                    default:
                        break;
                }

            }

            //累计结余
            weekForm.setSumResidue(parseData2(weekForm.getSumPlan() - weekForm.getSumExpend()));

            switch (type) {
                case "水":
                    weekForm.setYearPlan(plan.getWaterPlan());
                    break;
                case "电":
                    weekForm.setYearPlan(plan.getPowerPlan());
                    break;
                case "热":
                    weekForm.setYearPlan(plan.getHeatPlan());
                    break;
                default:
                    break;
            }
            weekForm.setType(type);
            weekForm.setMachineName(machineList.get(i).getName());
            weekForm.setMachineId(Math.toIntExact(machineList.get(i).getId()));
            weekForm.setWeekNum(weekNum);
            weekForm.setStartTime(startTime);
            weekForm.setStopTime(stopTime);
            weekForm.setDeleteFlag(false);
            weekFormList.add(weekForm);
        }

        for (WeekForm weekForm : weekFormList) {
            mapper.insert(weekForm);
        }
    }


    @Override
    public R<List<WeekForm>> selectByWeekNum(Integer weekNum, String year) {

        List<WeekForm> weekFormList = mapper.selectByWeekNum(weekNum, year);

        return R.ok("查询成功").putData(weekFormList);
    }


    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public R<List<Integer>> selectWeekNum(String type) {

        return R.ok("查询成功").putData(mapper.selectWeekNum(type));
    }

    @Override
    public void deleteWeekForm(Integer weekNum) {
        QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WeekForm::getWeekNum, weekNum);
        mapper.delete(queryWrapper);
    }

    @Override
    public R<List<WeekForm>> selectWeekForm(Integer weekNum, String type) {
        QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WeekForm::getWeekNum, weekNum).eq(WeekForm::getType, type);
        return R.ok("查询成功").putData(mapper.selectList(queryWrapper));
    }


    public static double parseData2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        String ds = df.format(d);
        return Double.valueOf(ds);
    }

    public static double parseData3(double d) {
        DecimalFormat df = new DecimalFormat("#.000");
        String ds = df.format(d);
        return Double.valueOf(ds);
    }


}
