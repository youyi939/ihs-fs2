package com.hsgrjt.fushun.ihs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.mapper.HeatNetworkDataMapper;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.hsgrjt.fushun.ihs.system.service.impl.MeterStaffServiceImpl.getDaysOfMonth;

@SpringBootTest
class IhsApplicationTests {

    @Autowired
    MeterStaffMapper mapper;

    @Autowired
    HeatMachineService heatMachineService;

    @Test
    void test(){
        User user = new User();
        user.setAllowCompanys("城南热电");
        List<HeatMachine> machineList = heatMachineService.getMachineByUser(user);


        for (HeatMachine heatMachine : machineList) {
            for (int j = 0; j < 9; j++) {
                Calendar myCalendar = new GregorianCalendar(2021, 12-1, j+1);
                Date date = myCalendar.getTime();

                MeterStaff meterStaff = new MeterStaff();
                meterStaff.setCenterStation(heatMachine.getCenterStation());
                meterStaff.setMachineName(heatMachine.getName());
                meterStaff.setMachineId(Integer.parseInt(heatMachine.getId()+""));
                meterStaff.setHeat(1);
                meterStaff.setPower(1);
                meterStaff.setWater(1);
                meterStaff.setGmtCreate(date);

                mapper.insert(meterStaff);
            }
        }

    }

}
