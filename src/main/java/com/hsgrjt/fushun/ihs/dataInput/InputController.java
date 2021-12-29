package com.hsgrjt.fushun.ihs.dataInput;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:  数据导入 ！！！仅用于测试环境
 * @Date: Create in  2021/12/29 下午10:54
 */
@RestController
public class InputController {

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    HeatMachineService heatMachineService;

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @PostMapping("/input/meter")
    @ApiOperation(value="导入水电热数据")
    public R inputMeter(@RequestBody InputMeter inputMeter){
        List<String> days = getDays("2020-11-1","2021-4-5");

        HeatMachine machine = heatMachineService.findById(inputMeter.getMachineId());

        for (int i = 0; i < days.size() ; i++) {
            String day = days.get(i);
            double iwater = inputMeter.getWater()[i];
            double ipower = inputMeter.getPower()[i];
            double iheat = inputMeter.getHeat()[i];
            insertMeter(day,iwater,ipower,iheat,machine);
        }

        return R.ok("数据导入成功");
    }

    @Async
    public void insertMeter(String day,double iwater,double ipower,double iheat,HeatMachine machine){
        MeterStaff meterStaff = new MeterStaff();
        meterStaff.setHeat(iheat);
        meterStaff.setPower(ipower);
        meterStaff.setWater(iwater);
        meterStaff.setMachineId(machine.getId().intValue());

        meterStaff.setMachineName(machine.getName());
        meterStaff.setCenterStation(machine.getCenterStation());

        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate2 = null;
        try {
            myDate2 = dateFormat2.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        meterStaff.setGmtCreate(myDate2);

        meterStaffMapper.insert(meterStaff);
    }


    /**
     * 获取两个日期之间的所有日期
     *
     * @param startTime 开始日期
     * @param endTime   结束日期
     * @return
     */
    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }

}
