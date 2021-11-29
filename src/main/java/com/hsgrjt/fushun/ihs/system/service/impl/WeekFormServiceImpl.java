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

import java.math.RoundingMode;
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
        int diffDays = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));

        int startMaxDay = getDaysOfMonth(startTime);
        int stopMaxDay = getDaysOfMonth(stopTime);

        //获取当前的机组列表
        List<HeatMachine> machineList = machineService.getMachineByUser(user);
        List<WeekForm> weekFormList = new ArrayList<>();

        for (int i = 0; i < machineList.size(); i++) {
            Plan plan = planService.selectByStationName(machineList.get(i).getName());
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
                double d = parseData((monthScale.get(startMonth) / startMaxDay) * diffDays, 3);
                weekForm.setThisWeekRatio(d);
            } else {
                int maxStart = getDaysOfMonth(startTime);
                //包含的第一个月的天数
                int startMonthDay = maxStart - startDay;

                int maxStop = getDaysOfMonth(stopTime);
                //包含的第二个月的天数
                double d = parseData(((monthScale.get(startMonth) / maxStart)) * startMonthDay + ((monthScale.get(stopMonth) / maxStop)) * stopDay, 3);
                weekForm.setThisWeekRatio(d);
            }

            //本周计划指标
            double thisWeekPlan = parseData(plan.getWaterPlan() * plan.getArea() * weekForm.getThisWeekRatio() / 1000, 2);
            weekForm.setThisWeekPlan(thisWeekPlan);

            //上周实耗，第一周的数据都为0，第二周开始本数据为上周的本周实耗
            //累计计划指标 -第一周等于本周计划指标，第二周等于本周计划指标+上一周计划指标
            //累计实耗
            if (weekNum == 1) {
                weekForm.setLastWeekExpend(0);
                weekForm.setSumPlan(weekForm.getThisWeekPlan());
                weekForm.setSumExpend(0);
            } else {
                QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(WeekForm::getCompany, user.getAllowCompanys()).eq(WeekForm::getMachineId, machineList.get(i).getId()).eq(WeekForm::getWeekNum, weekNum - 1);
                WeekForm lastWeekForm = mapper.selectOne(queryWrapper);
                weekForm.setLastWeekExpend(lastWeekForm.getThisWeekExpend());
                weekForm.setSumPlan(lastWeekForm.getThisWeekPlan() + weekForm.getThisWeekPlan());
                weekForm.setSumExpend(lastWeekForm.getThisWeekExpend() + weekForm.getThisWeekExpend());
            }

            //本周实耗
            MeterStaff startMeter = meterStaffMapper.selectByTime(startYear, startMonth, startDay, machineList.get(i).getId());
            MeterStaff stopMeter = meterStaffMapper.selectByTime(stopYear, stopMonth, stopDay, machineList.get(i).getId());
            if (V.isEmpty(startMeter) || V.isEmpty(stopMeter)) {
                weekForm.setThisWeekExpend(0);
            } else {
                weekForm.setThisWeekExpend(stopMeter.getWater() - startMeter.getWater());
            }

            //本周结余
            weekForm.setThisWeekResidue(weekForm.getThisWeekPlan() - weekForm.getThisWeekExpend());

            //本周单耗-去年本周单耗
            if (weekForm.getArea() == 0 || weekForm.getThisWeekExpend() == 0 || weekForm.getThisWeekResidue() == 0) {
                weekForm.setThisWeekUnit(0);
            } else {
                double d = (weekForm.getThisWeekExpend() / weekForm.getArea() / weekForm.getThisWeekResidue()) * 1000;
                d = parseData(d, 3);
                weekForm.setThisWeekUnit(d);
            }
            weekForm.setLastYearThisWeekUnit(0);


            //计算累计单耗
            //开始和结束日期都是11月份，证明没有跨月的情况
            if (startMonth == Calendar.DECEMBER && stopMonth == Calendar.DECEMBER) {
                double n = 0;
                n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(11) / startMaxDay * stopDay) * 1000;
                //0/0==NAN没法计算
                if (weekForm.getArea() == 0.0) {
                    weekForm.setSumUnit(0);
                } else {
                    weekForm.setSumUnit(parseData(n, 2));
                }
            } else if (startMonth != stopMonth) {
                double n = 0;
                n = weekForm.getSumExpend() / weekForm.getArea() / (monthScale.get(startMonth) + monthScale.get(stopMonth) / stopMaxDay * stopDay) * 1000;
                weekForm.setSumUnit(n);
            }

            //累计结余
            weekForm.setSumResidue(weekForm.getSumPlan() - weekForm.getSumExpend());

            weekForm.setYearPlan(plan.getWaterPlan());
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

        List<WeekForm> weekFormList = mapper.selectByWeekNum(weekNum,year);

        return R.ok("查询成功").putData(weekFormList);
    }

    /**
     * 保留小数点后几位的方法
     *
     * @param d   小数
     * @param num 保留到小数点后几位
     * @return 保留后的小数
     */
    private static double parseData(double d, int num) {

        NumberFormat nf = NumberFormat.getNumberInstance();
// 保留两位小数
        nf.setMaximumFractionDigits(num);
// 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        return Double.parseDouble(nf.format(d).trim());
    }


    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public R<List<Integer>> selectWeekNum() {

        return R.ok("查询成功").putData(mapper.selectWeekNum());
    }

    @Override
    public void deleteWeekForm(Integer weekNum) {
        QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WeekForm::getWeekNum,weekNum);
        mapper.delete(queryWrapper);
    }

    @Override
    public R<List<WeekForm>> selectWeekForm(Integer weekNum, String type) {
        QueryWrapper<WeekForm> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(WeekForm::getWeekNum,weekNum).eq(WeekForm::getType,type);
        return R.ok("查询成功").putData(mapper.selectList(queryWrapper));
    }
}
