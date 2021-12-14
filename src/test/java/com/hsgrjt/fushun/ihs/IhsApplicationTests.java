package com.hsgrjt.fushun.ihs;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.MeterStaff;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.mapper.HeatNetworkDataMapper;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.HeatNetworkDataService;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SpringBootTest
class IhsApplicationTests {

    @Autowired
    MeterStaffMapper mapper;

    @Autowired
    MeterStaffService meterStaffService;

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @Autowired
    HeatMachineService heatMachineService;

//    @Test
    void test1() throws ParseException {

        User user1 = new User();
        user1.setAllowCompanys("城南热电");
        List<HeatMachine> machineList1 = heatMachineService.getMachineByUser(user1);

        User user2 = new User();
        user2.setAllowCompanys("抚顺新北方");
        List<HeatMachine> machineList2 = heatMachineService.getMachineByUser(user2);

        User user3 = new User();
        user3.setAllowCompanys("新北方高湾");
        List<HeatMachine> machineList3 = heatMachineService.getMachineByUser(user3);

        for (int i = 0; i < machineList1.size(); i++) {
            initMeterData(machineList1.get(i).getId());
        }

        for (int i = 0; i < machineList2.size(); i++) {
            initMeterData(machineList2.get(i).getId());
        }

        for (int i = 0; i < machineList3.size(); i++) {
            initMeterData(machineList3.get(i).getId());
        }

    }

    private void initMeterData(Long id) throws ParseException {
        MeterStaffAddDTO meterStaffAddDTO = new MeterStaffAddDTO();
        meterStaffAddDTO.setHeat(0);
        meterStaffAddDTO.setPower(0);
        meterStaffAddDTO.setWater(0);
        meterStaffAddDTO.setMachineId(id.intValue());
        save(meterStaffAddDTO);
    }

    private void save(MeterStaffAddDTO dto) throws ParseException {
        MeterStaff entity = new MeterStaff();
        BeanUtils.copyProperties(dto, entity);
        entity.setGmtCreate(new Date());

        HeatMachine machine = heatMachineService.findById(dto.getMachineId());
        entity.setMachineName(machine.getName());
        entity.setCenterStation(machine.getCenterStation());


        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate2 = dateFormat2.parse("2021-12-12");
        entity.setGmtCreate(myDate2);

        meterStaffMapper.insert(entity);
    }


    @Autowired
    HeatNetworkDataService heatNetworkDataService;
    @Autowired
    HeatNetworkDataMapper heatNetworkDataMapper;

    /**
     * 批量生成去年11月-3月的所有数据（初始化数据，所有数据为0）
     *
     * @throws ParseException
     */

//    @Test
    void test() throws ParseException {

        List<String> days = getDays("2020-11-1", "2021-3-31");

        double[] one_8 = {
                31,
                31.5,
                32,
                32,
                31,
                31.5,
                31.5,
                32.5,
                32.5,
                31.5,
                30,
                31,
                31.5,
                30.5,
                31,
                30.5,
                30,
                34,
                35,
                33.5,
                34,
                34.5,
                34,
                35,
                35,
                34.7,
                34.5,
                34.5,
                35,
                36,
                36,
                37,
                36,
                36,
                36,
                35.5,
                37,
                38,
                38,
                38,
                38,
                40,
                41,
                41,
                41,
                41,
                40,
                40,
                42,
                41,
                42,
                41,
                41,
                40.5,
                40.5,
                39.5,
                39.5,
                40,
                41,
                41.5,
                41.5,
                41.5,
                41.5,
                41.5,
                42.5,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                41.5,
                41,
                41,
                41,
                41,
                41,
                40.7,
                39.5,
                38.8,
                38.5,
                38,
                37.8,
                37,
                37,
                37,
                38,
                38,
                37.5,
                37,
                37,
                38,
                38.5,
                38,
                36.5,
                36.5,
                36,
                36,
                36,
                35.5,
                35.5,
                36.5,
                36,
                36,
                36.5,
                36.5,
                36.5,
                36.5,
                36,
                36,
                35.5,
                35.5,
                35.5,
                34,
                33.5,
                32.5,
                33.5,
                33,
                34,
                32,
                32,
                32,
                33.5,
                34.5,
                32,
                31,
                31,
                31,
                31,
                31,
                31,
                32,
                31,
                31.5,
                31,
                31,
                30.5,
                30.5,
                31.5,
                31,
                30.5,
                30.5,
                29.5,
                29.5,
                29.8,
                29.8,
                29.8,
                29.8,
                29.8
        };

        double[] Nine_16 = {
                33,
                33.5,
                34,
                33.5,
                33,
                33.5,
                34,
                34,
                34,
                33.5,
                32.5,
                32.5,
                33.5,
                33,
                33,
                33,
                32.5,
                34.5,
                35,
                34.5,
                34.5,
                35,
                35.5,
                36,
                36,
                35.7,
                35.5,
                36.5,
                36.5,
                37,
                37,
                37,
                37,
                37,
                37,
                36.5,
                39,
                39,
                39,
                39,
                40,
                41,
                41,
                42,
                42,
                42,
                41,
                41,
                42,
                42,
                42,
                41,
                41,
                40.5,
                40.5,
                39.5,
                39.5,
                40,
                42,
                41.5,
                41.5,
                41.5,
                41.5,
                41.5,
                42.5,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                41.5,
                41,
                41,
                41,
                41,
                41,
                40.7,
                39.5,
                38.8,
                38.5,
                38,
                37.8,
                37,
                37,
                37,
                38,
                38,
                37.5,
                37,
                37,
                38,
                38.5,
                38,
                36.5,
                36.5,
                36,
                36,
                36,
                35.5,
                35.5,
                36.5,
                36,
                36,
                36.5,
                36.5,
                36.5,
                36.5,
                36,
                36,
                35.5,
                35.5,
                35.5,
                34,
                33.5,
                32.5,
                33.5,
                33,
                34,
                32,
                32,
                32,
                33.5,
                34.5,
                34,
                33,
                33,
                32,
                32,
                31.5,
                31.5,
                31,
                32,
                31.5,
                31,
                31,
                30.5,
                30.5,
                32,
                31.5,
                30.5,
                30.5,
                29.5,
                29.5,
                30,
                30,
                31,
                31,
                31
        };

        double[] shiqi = {
                31.5,
                31.5,
                34.5,
                33.5,
                33.5,
                33.5,
                35,
                35,
                35,
                33.5,
                33,
                33.5,
                34.5,
                33,
                33,
                33,
                33,
                35,
                35,
                35,
                35,
                36,
                36.5,
                36.5,
                37,
                36.7,
                36.5,
                37.5,
                37.5,
                38,
                38,
                38,
                38,
                38,
                38,
                38,
                39,
                39,
                39,
                39,
                40,
                41,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                41,
                41,
                40.5,
                40.5,
                39.5,
                39.5,
                41,
                42,
                41.5,
                41.5,
                41.5,
                41.5,
                41.5,
                42.5,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                42,
                41.5,
                41,
                41,
                41,
                41,
                41,
                40.7,
                39.5,
                38.8,
                38.5,
                38,
                37.8,
                37,
                37,
                37,
                38,
                39,
                37.5,
                37,
                37,
                38,
                39,
                38,
                36.5,
                36.5,
                37,
                37,
                36,
                36,
                36,
                36.5,
                36,
                36,
                36.5,
                36.5,
                36.5,
                36.5,
                36,
                36,
                35.5,
                35.5,
                35.5,
                35,
                35.5,
                35.5,
                34.5,
                34,
                34.5,
                34,
                32,
                32,
                35,
                34.5,
                34,
                33,
                33,
                32,
                32,
                31.5,
                31.5,
                31,
                32,
                31.5,
                31,
                31,
                30.5,
                30.5,
                32,
                31.5,
                30.5,
                30.5,
                29.5,
                29.5,
                30,
                30,
                31,
                31,
                31
        };

        initData(days, 16, one_8, Nine_16, shiqi);

    }

    public void initData(List<String> days, Integer stationId, double[] one_8, double[] Nine_16, double[] shiqi) throws ParseException {
        for (int i = 0; i < days.size(); i++) {

            DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
            Date myDate2 = dateFormat2.parse(days.get(i).toString() + "/" + 5 + ":00:00");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String[] temp = formatter.format(myDate2).split("-");
            int year = Integer.parseInt(temp[0]);
            int month = Integer.parseInt(temp[1]);
            int day = Integer.parseInt(temp[2]);
            heatNetworkDataMapper.updateByTime(one_8[i],year,month,day,5,stationId);
            heatNetworkDataMapper.updateByTime(Nine_16[i],year,month,day,10,stationId);
            heatNetworkDataMapper.updateByTime(shiqi[i],year,month,day,20,stationId);

        }
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
