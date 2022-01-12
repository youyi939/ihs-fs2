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
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//@SpringBootTest
class IhsApplicationTests {

    @Autowired
    MeterStaffMapper mapper;

    @Autowired
    MeterStaffService meterStaffService;

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @Autowired
    HeatMachineService heatMachineService;

    @Autowired
    PlanService planService;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

//    @Test
    void testPlan(){
//        planService.initData("抚顺新北方");
//        planService.initData("新北方高湾");


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);
        String[] temp = dateNowStr.split("-");
        MeterStaff meterStaff = meterStaffMapper.selectByTime(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]),(long)1);
        if (V.isEmpty(meterStaff)){
            System.out.println("为空");
        }else {
            System.out.println("不为空");
        }
    }

//    @Test
    void test1() throws ParseException {

        User user3 = new User();
        user3.setAllowCompanys("新北方高湾");
        List<HeatMachine> machineList3 = heatMachineService.getMachineByUser(user3);

        List<String> days = getDays("2021-12-17","2022-1-12");
        for (String day : days) {
            for (int i = 0; i < machineList3.size(); i++) {
                initMeterData(machineList3.get(i),day);
            }
        }



    }


    /**
     * 导入指定日期的水电热数据
     */
//    @Test
    void insertMeterData() throws ParseException, InterruptedException {

        double[] water = {
                665,
                726,
                794,
                838,
                867,
                896,
                920,
                941,
                965,
                985,
                997,
                1014,
                1037,
                1060,
                1083,
                1156,
                1183,
                1213,
                1244,
                1278,
                1309,
                1347,
                1380,
                1412,
                1440,
                1470,
                1499,
                1535,
                1586,
                1617,
                1652,
                1692,
                1734,
                1776,
                1814,
                1862,
                1901,
                1928,
                1968,
                1998,
                2029,
                2060,
                2098,
                2134,
                2166,
                2205,
                2240,
                2275,
                2315,
                2372,
                2421,
                2457,
                2491,
                2527,
                2561,
                2597,
                2625,
                2648,
                2676,
                2706,
                2734,
                2761,
                2784,
                2810,
                2841,
                2895,
                2987,
                3085,
                3195,
                3294,
                3386,
                3450,
                3507,
                3561,
                3616,
                3707,
                3761,
                3823,
                3886,
                3942,
                4010,
                4077,
                4139,
                4210,
                4266,
                4322,
                4383,
                4435,
                4500,
                4561,
                4607,
                4663,
                4721,
                4776,
                4830,
                4896,
                4960,
                5002,
                5048,
                5094,
                5137,
                5182,
                5236,
                5286,
                5330,
                5374,
                5427,
                5478,
                5520,
                5562,
                5606,
                5651,
                5696,
                5737,
                5776,
                5821,
                5866,
                5915,
                5957,
                5999,
                6051,
                6095,
                6130,
                6159,
                6188,
                6217,
                6248,
                6286,
                6317,
                6348,
                6380,
                6410,
                6441,
                6473,
                6506,
                6532,
                6564,
                6598,
                6627,
                6660,
                6691,
                6722,
                6752,
                6781,
                6811,
                6844,
                6875,
                6907,
                6940,
                6970,
                7008,
                7036,
                7066,
                7095,
                7125,
                7157
        };

        double[] power = {
                39401,
                39417,
                39432,
                39448,
                39464,
                39479,
                39496,
                39513,
                39531,
                39548,
                39566,
                39583,
                39601,
                39619,
                39637,
                39657,
                39672,
                39687,
                39702,
                39717,
                39732,
                39747,
                39762,
                39778,
                39794,
                39809,
                39824,
                39839,
                39855,
                39870,
                39886,
                39901,
                39916,
                39932,
                39947,
                39962,
                39977,
                39993,
                40008,
                40024,
                40039,
                40055,
                40071,
                40089,
                40106,
                40124,
                40142,
                40162,
                40183,
                40203,
                40223,
                40243,
                40262,
                40282,
                40302,
                40321,
                40341,
                40360,
                40381,
                40403,
                40427.01,
                40450,
                40471,
                40494,
                40517,
                40540,
                40561,
                40581,
                40602,
                40624.32,
                40647.76,
                40671,
                40695,
                40718,
                40742,
                40766.12,
                40789,
                40813,
                40836,
                40860.42,
                40883,
                40905,
                40925,
                40946.67,
                40964.18,
                40981.88,
                41000.87,
                41019,
                41039,
                41059,
                41079.22,
                41099.15,
                41118.21,
                41137.2,
                41155.87,
                41175.48,
                41193.51,
                41211.55,
                41229,
                41248.09,
                41266.12,
                41280.2,
                41296.2,
                41316.44,
                41334.08,
                41352.84,
                41371.08,
                41389.36,
                41407.58,
                41424.44,
                41440.05,
                41455.34,
                41470.55,
                41485.16,
                41498.95,
                41513.36,
                41527.51,
                41541.76,
                41556.94,
                41571.94,
                41587.13,
                41602.3,
                41617.31,
                41632.45,
                41646.16,
                41660.44,
                41674.5,
                41688.57,
                41702.81,
                41715.83,
                41729.55,
                41742.89,
                41756.49,
                41769.82,
                41782.76,
                41796.65,
                41809.91,
                41823.24,
                41836.7,
                41849.72,
                41863.48,
                41877.24,
                41890.21,
                41903.43,
                41915.78,
                41929.11,
                41941.99,
                41954.94,
                41968.05,
                41980.43,
                41994.58,
                42006.65,
                42019.57,
                42032.61,
                42045.28,
                42058.58,
        };

        double[] heat = {
                110053,
                110273,
                110463,
                110716,
                110945,
                111132,
                111320,
                111530,
                111749,
                111974,
                112217,
                112423,
                112624,
                112859,
                113003,
                113275,
                113472,
                113673,
                113887,
                114186,
                114495,
                114814,
                115170,
                115488,
                115788,
                116101,
                116414,
                116728,
                117066,
                117406,
                117741.2,
                118094.4,
                118461.9,
                118844.6,
                119237.4,
                119610,
                119975,
                120358,
                120725,
                121083,
                121455,
                121827,
                122216,
                122649,
                123076,
                123503,
                123971,
                124389,
                124831,
                125288.5,
                125734,
                126181,
                126579,
                126964,
                127355,
                127759.1,
                128172,
                128574,
                129027,
                129518,
                130020,
                130492,
                130946.3,
                131427.5,
                131931,
                132391.3,
                132896,
                133400,
                133949,
                134474.7,
                135031.2,
                135540,
                136023,
                136488,
                136945,
                137443.7,
                137935,
                138467,
                138957,
                139470,
                139940,
                140355,
                140753,
                141143.7,
                141518.6,
                141864.7,
                142225.3,
                142596.1,
                142985,
                143441.7,
                143875.6,
                144303,
                144720.4,
                145131.6,
                145566.6,
                146043.3,
                146398.8,
                146772.5,
                147149,
                147504.7,
                147854.8,
                148239.9,
                148592.7,
                148943.8,
                149259.8,
                149582.6,
                149925.2,
                150284,
                150637.1,
                151022.7,
                151399.9,
                151737.6,
                152042.8,
                152322.1,
                152612.2,
                152917.8,
                153229,
                153521.7,
                153813.3,
                154063.4,
                154356,
                154657,
                154939,
                155207.5,
                155434.2,
                155747.6,
                156040.7,
                156303.7,
                156531.9,
                156737.4,
                156907.7,
                157124.7,
                157338.3,
                157523.9,
                157707.2,
                157924.3,
                158128.4,
                158313.1,
                158544.7,
                158777.4,
                158981.2,
                159218.3,
                159411.4,
                159609,
                159752.6,
                159912,
                160051.7,
                160196.3,
                160349.4,
                160489.4,
                160602.8,
                160716,
                160820.2,
                160900.5,
                161015.3,
                161101,
        };

        List<String> days = getDays("2020-11-1","2021-4-5");

        HeatMachine machine = heatMachineService.findById(17);

        for (int i = 0; i < days.size() ; i++) {
            String day = days.get(i);
            double iwater = water[i];
            double ipower = power[i];
            double iheat = heat[i];

            threadPoolTaskExecutor.submit(new Thread(()->{

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
            }));

        }

        System.out.println("************主线程休眠***********");
        Thread.sleep(24 * 60 * 60 * 1000 + 10 * 60 * 1000);

    }


    private void initMeterData(HeatMachine heatMachine,String day) throws ParseException {
        MeterStaff meterStaff = new MeterStaff();
        meterStaff.setHeat(0);
        meterStaff.setPower(0);
        meterStaff.setWater(0);
        meterStaff.setMachineId(heatMachine.getId().intValue());

        meterStaff.setMachineName(heatMachine.getName());
        meterStaff.setCenterStation(heatMachine.getCenterStation());

        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate2 = dateFormat2.parse(day);
        meterStaff.setGmtCreate(myDate2);

        meterStaffMapper.insert(meterStaff);
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
