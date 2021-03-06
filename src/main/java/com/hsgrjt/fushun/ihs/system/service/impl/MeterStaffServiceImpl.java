package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.*;
import com.hsgrjt.fushun.ihs.system.entity.dto.DayFormDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterUpdateDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: KenChen
 * @Description: 水电热表的service实现类
 * @Date: Create in  2021/11/19 下午2:40
 */
@Service
public class MeterStaffServiceImpl implements MeterStaffService {

    @Autowired
    MeterStaffMapper meterStaffMapper;

    @Autowired
    UserService userService;

    @Autowired
    HeatMachineService machineService;

    @Autowired
    PlanService planService;


    @Override
    public void save(MeterStaffAddDTO dto) {
        MeterStaff entity = new MeterStaff();
        BeanUtils.copyProperties(dto, entity);
        entity.setGmtCreate(new Date());

        HeatMachine machine = machineService.findById(dto.getMachineId());
        entity.setMachineName(machine.getName());
        entity.setCenterStation(machine.getCenterStation());

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);
        String[] temp = dateNowStr.split("-");
        MeterStaff meterStaff = meterStaffMapper.selectByTime(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]),(long)dto.getMachineId());
        if (V.isEmpty(meterStaff)){
            meterStaffMapper.insert(entity);
        }
    }

    @Override
    public R<List<MeterDataDTO>> findAll(User user, String type, Integer selectYear, Integer selectMonth) {
        //获取用户所在公司下的机组列表
        List<HeatMachine> machineList = machineService.getMachineByUser(user);

        //该方法返回的数据dto
        List<MeterDataDTO> meterDataDTOList = new ArrayList<>();

        //调用方法，传入dto集合，机组集合，数据类型，即可获得这些机组下的某种类型的数据
        getMachineMeterStaffData(meterDataDTOList, machineList, type, selectYear, selectMonth);

        //本月最大天数
        int maxDays = getDaysOfMonth(selectYear, selectMonth - 1);


        //补全数据，如果数据库里的数据不足月天数，则填写虚假数据加入到list中
        for (MeterDataDTO meterDataDTO : meterDataDTOList) {
            List<MeterData> sourceData = meterDataDTO.getMeterDataList();
            for (int j = 1; j <= maxDays ; j++) {
                if (sourceData.size() >= j) {

                } else {
                    MeterData meterData = new MeterData();
                    meterData.setMeterData(0);
                    meterData.setMeterTime(selectYear + "-" + selectMonth + "-" + j);
                    sourceData.add(meterData);
                }
            }
            meterDataDTO.setMeterDataList(sourceData);
        }

        //将查询结果排序
        for (MeterDataDTO meterDataDTO : meterDataDTOList) {
            meterDataDTO.getMeterDataList().sort(new Comparator<MeterData>() {
                @Override
                public int compare(MeterData o1, MeterData o2) {
                    String temp1 = o1.getMeterTime().split("-")[2];
                    String temp2 = o2.getMeterTime().split("-")[2];
                    int diff = Integer.parseInt(temp1) - Integer.parseInt(temp2);

                    if (diff > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
            System.out.println(meterDataDTO.getMeterDataList().size());
        }




        return R.ok("查询成功").putData(meterDataDTOList);
    }



    /**
     * 查看日报表
     *
     * @param user
     * @return
     */
    @Override
    public R<List<DayFormDTO>> getDayFromWater(User user, Integer selectYear, Integer selectMonth, String type) {
        List<DayFormDTO> dayFormDTOList = new ArrayList<>();

        List<String> centerStations = machineService.getCenterStation(user.getAllowCompanys());
        List<HeatMachine> machineList = machineService.getMachineByUser(user);

        //这个是需要set进dayReport中的list,这个list中的对象存的就是具体的数据
        List<MeterDataDTO> meterDataDTOList = new ArrayList<>();
        //获得机组的水电热数据

        switch (type) {
            case "水":
                getMachineMeterStaffData(meterDataDTOList, machineList, "水", selectYear, selectMonth);
                break;
            case "电":
                getMachineMeterStaffData(meterDataDTOList, machineList, "电", selectYear, selectMonth);
                break;
            case "热":
                getMachineMeterStaffData(meterDataDTOList, machineList, "热", selectYear, selectMonth);
                break;
            default:
                break;
        }

        //计算日指标，月度比例
        Map<Integer, Double> monthScale = new HashMap<Integer, Double>();
        monthScale.put(11, 0.17);
        monthScale.put(12, 0.25);
        monthScale.put(1, 0.25);
        monthScale.put(2, 0.19);
        monthScale.put(3, 0.14);

        //本月最大天数
        int maxDays = getDaysOfMonth(selectYear, selectMonth - 1);
        //上个月最大天数
        int maxDayLast = getDaysOfMonth(selectYear, selectMonth - 2);
        //下个月最天数
        int maxDayNext = getDaysOfMonth(selectYear, selectMonth);


        //计算基础数据
        for (int i = 0; i < machineList.size(); i++) {
            String stationName = machineList.get(i).getName();
            DayFormDTO dayFormDTO = new DayFormDTO();
            dayFormDTO.setCenterStation(machineList.get(i).getCenterStation());
            dayFormDTO.setStationName(stationName);

            double bigSum = 0;
            Plan plan = planService.selectByStationName(stationName,machineList.get(i).getId().intValue());
            List<MeterData> sourceData = meterDataDTOList.get(i).getMeterDataList();
            List<MeterData> targetdata = new ArrayList<>();
            for (int j = 0; j < maxDays; j++) {
                MeterData meterData = new MeterData();
                //避免月底的情况，数组越界。月底单独获取，单独计算
                if (j < sourceData.size() - 1) {
                    meterData.setMeterTime(sourceData.get(j).getMeterTime());
                    double data = sourceData.get(j + 1).getMeterData() - sourceData.get(j).getMeterData();
                    meterData.setMeterData(parseData2(data));
                    bigSum += data;
                    targetdata.add(meterData);
                } else if (j == sourceData.size() - 1 && sourceData.size() == maxDays) {
                    double meterData1 = sourceData.get(j).getMeterData();
                    if (selectMonth == 12) {
                        MeterStaff meterStaff2 = meterStaffMapper.selectByTime(selectYear + 1, 1, 1, machineList.get(i).getId());
                        if (!V.isEmpty(meterStaff2)) {
                            double meterData2 = meterStaff2.getWater();
                            meterData.setMeterData(parseData2(meterData2 - meterData1));
                            bigSum += meterData2 - meterData1;
                            targetdata.add(meterData);
                        } else {
                            double data = 0;
                            meterData.setMeterData(data);
                            bigSum += data;
                            targetdata.add(meterData);
                        }
                    } else {
                        MeterStaff meterStaff2 = meterStaffMapper.selectByTime(selectYear, selectMonth + 1, 1, machineList.get(i).getId());
                        if (!V.isEmpty(meterStaff2)) {
                            double meterData2 = meterStaff2.getWater();
                            meterData.setMeterData(parseData2(meterData2 - meterData1));
                            meterData.setMeterTime(sourceData.get(j - 1).getMeterTime());
                            bigSum += meterData2 - meterData1;
                            targetdata.add(meterData);
                        } else {
                            double data = 0;
                            meterData.setMeterData(data);
                            bigSum += data;
                            targetdata.add(meterData);
                        }
                    }

                } else {
                    double data = 0;
                    meterData.setMeterData(data);
                    bigSum += data;
                    targetdata.add(meterData);
                }
            }

            dayFormDTO.setMeterDataList(targetdata);
            dayFormDTO.setBigSum(parseData2(bigSum));


            //日指标计算
            double dayTarget = 0;
            switch (type) {
                case "水":
                    dayTarget = (plan.getWaterPlan() * plan.getArea() / 1000) * monthScale.get(selectMonth) / maxDays;
                    dayFormDTO.setYearPlan(plan.getWaterPlan());
                    dayFormDTO.setYearPlanResidue(plan.getWaterPlan() - bigSum);
                    break;
                case "电":
                    dayTarget = (plan.getPowerPlan() * plan.getArea() ) * monthScale.get(selectMonth) / maxDays;
                    dayFormDTO.setYearPlan(plan.getPowerPlan());
                    dayFormDTO.setYearPlanResidue(plan.getPowerPlan() - bigSum);
                    break;
                case "热":
                    dayTarget = (1/plan.getHeatPlan() * plan.getArea() ) * monthScale.get(selectMonth) / maxDays;
                    dayFormDTO.setYearPlan(plan.getHeatPlan());
                    dayFormDTO.setYearPlanResidue(plan.getHeatPlan() - bigSum);
                    break;
                default:
                    break;
            }

            dayFormDTO.setDayTarget(parseData2(dayTarget));
            dayFormDTOList.add(dayFormDTO);
        }

        //计算中心站小记

        for (String centerStation : centerStations) {
            DayFormDTO centerDtoXiaoji = new DayFormDTO();
            centerDtoXiaoji.setStationName(centerStation + "小记");
            DayFormDTO centerDtoJieyu = new DayFormDTO();
            centerDtoJieyu.setStationName(centerStation + "结余");
            System.out.println(centerStation);
            List<MeterData> xiaojiListCenter = new ArrayList<>();
            List<MeterData> jieyuListCenter = new ArrayList<>();
            //循环遍历数据对象，查看哪个对象的中心站属性跟中心站能对应上
            double targetDayPlan = 0;

            for (int j = 0; j < maxDays; j++) {

                MeterData meterData = new MeterData();
                double smallSum = 0;
                for (DayFormDTO dayFormDTO : dayFormDTOList) {
                    if (dayFormDTO.getCenterStation() != null && dayFormDTO.getCenterStation().equals(centerStation)) {
                        smallSum += dayFormDTO.getMeterDataList().get(j).getMeterData();
                    }
                }
                meterData.setMeterData(parseData2(smallSum));
                xiaojiListCenter.add(meterData);
            }

            //计算日指标小记
            for (DayFormDTO dayFormDTO : dayFormDTOList) {
                if (dayFormDTO.getCenterStation() != null && dayFormDTO.getCenterStation().equals(centerStation)) {
                    targetDayPlan += dayFormDTO.getDayTarget();
                }
            }
            centerDtoXiaoji.setDayTarget(parseData2(targetDayPlan));
            centerDtoXiaoji.setBigSum(parseData2(xiaojiListCenter.stream().mapToDouble(MeterData::getMeterData).sum()));
            centerDtoXiaoji.setMeterDataList(xiaojiListCenter);
            dayFormDTOList.add(centerDtoXiaoji);

            for (int i = 0; i < maxDays; i++) {
                MeterData meterData = new MeterData();
                double data1 = 0;
                data1 = centerDtoXiaoji.getDayTarget() - centerDtoXiaoji.getMeterDataList().get(i).getMeterData();
                meterData.setMeterData(parseData2(data1));
                jieyuListCenter.add(meterData);
            }
            centerDtoJieyu.setMeterDataList(jieyuListCenter);
            centerDtoJieyu.setDayTarget(parseData2(targetDayPlan));
            centerDtoJieyu.setBigSum(parseData2(jieyuListCenter.stream().mapToDouble(MeterData::getMeterData).sum()));
            dayFormDTOList.add(centerDtoJieyu);

        }

        DayFormDTO zongXiaojiDTO = new DayFormDTO();
        DayFormDTO zongJieyuDTO = new DayFormDTO();
        zongJieyuDTO.setStationName("总结余");
        zongXiaojiDTO.setStationName("总小记");
        List<MeterData> zongXiaojiList = new ArrayList<>();
        List<MeterData> zongJieyuList = new ArrayList<>();


        for (int i = 0; i < maxDays; i++) {
            MeterData meterData = new MeterData();
            MeterData meterData2 = new MeterData();
            double xiaoji = 0;
            double jieyu = 0;
            for (int j = 0; j < dayFormDTOList.size(); j++) {

                for (int k = 0; k < centerStations.size(); k++) {
                    if (dayFormDTOList.get(j).getStationName().equals(centerStations.get(k) + "小记")) {
                        System.out.println(dayFormDTOList.get(j).getMeterDataList().get(i).getMeterData());
                        xiaoji += dayFormDTOList.get(j).getMeterDataList().get(i).getMeterData();
                    } else if (dayFormDTOList.get(j).getStationName().equals(centerStations.get(k) + "结余")) {
                        System.out.println(dayFormDTOList.get(j).getMeterDataList().get(i).getMeterData());
                        jieyu += dayFormDTOList.get(j).getMeterDataList().get(i).getMeterData();
                    }
                }
            }
            meterData.setMeterData(parseData2(xiaoji));
            meterData2.setMeterData(parseData2(jieyu));
            zongJieyuList.add(meterData2);
            zongXiaojiList.add(meterData);
        }

        double target = 0;
        double bigsum = 0;
        //合计和日指标
        for (int j = 0; j < dayFormDTOList.size(); j++) {

            for (int k = 0; k < centerStations.size(); k++) {
                if (dayFormDTOList.get(j).getStationName().equals(centerStations.get(k) + "小记")) {
                    target += dayFormDTOList.get(j).getDayTarget();
                    bigsum += dayFormDTOList.get(j).getBigSum();
                }
            }
        }

        zongJieyuDTO.setBigSum(parseData2(zongJieyuList.stream().mapToDouble(MeterData::getMeterData).sum()));
        zongXiaojiDTO.setBigSum(parseData2(bigsum));
        zongXiaojiDTO.setDayTarget(parseData2(target));
        zongJieyuDTO.setMeterDataList(zongJieyuList);
        zongXiaojiDTO.setMeterDataList(zongXiaojiList);
        dayFormDTOList.add(zongXiaojiDTO);
        dayFormDTOList.add(zongJieyuDTO);

        return R.ok("查询成功").putData(dayFormDTOList);
    }

    @Override
    public void initDataEveryDay() {
        User user1 = new User();
        user1.setAllowCompanys("城南热电");
        List<HeatMachine> machineList1 = machineService.getMachineByUser(user1);

        User user2 = new User();
        user2.setAllowCompanys("抚顺新北方");
        List<HeatMachine> machineList2 = machineService.getMachineByUser(user2);

        User user3 = new User();
        user3.setAllowCompanys("新北方高湾");
        List<HeatMachine> machineList3 = machineService.getMachineByUser(user3);

        for (int i = 0; i < machineList1.size(); i++) {
            initData(machineList1.get(i).getId());
        }

        for (int i = 0; i < machineList2.size(); i++) {
            initData(machineList2.get(i).getId());
        }

        for (int i = 0; i < machineList3.size(); i++) {
            initData(machineList3.get(i).getId());
        }

    }

    @Override
    public void update(MeterUpdateDTO dto) {
        switch (dto.getType()) {
            case "水":
                meterStaffMapper.updateWater(dto.getUpdateData(), dto.getId());
                break;
            case "电":
                meterStaffMapper.updatePower(dto.getUpdateData(), dto.getId());
                break;
            case "热":
                meterStaffMapper.updateHeat(dto.getUpdateData(), dto.getId());
                break;
            default:
                break;
        }
    }


    @Override
    public R updateByMachineId(Integer machineId, double water, double power, double heat) {


        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String[] temp = formatter.format(date).split("-");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = Integer.parseInt(temp[2]);

        if (!V.isEmpty( meterStaffMapper.selectByTime(year,month,day, machineId.longValue())) ){
            meterStaffMapper.updateHeatByMachineId(machineId,water,power,heat,year,month,day);
            return R.ok("更新成功");
        }

        MeterStaffAddDTO meterStaffAddDTO = new MeterStaffAddDTO();
        meterStaffAddDTO.setHeat(heat);
        meterStaffAddDTO.setPower(power);
        meterStaffAddDTO.setWater(water);
        meterStaffAddDTO.setMachineId(machineId);
        save(meterStaffAddDTO);
        return R.ok("更新成功");
    }


    private void initData(Long id) {
        MeterStaffAddDTO meterStaffAddDTO = new MeterStaffAddDTO();
        meterStaffAddDTO.setHeat(0);
        meterStaffAddDTO.setPower(0);
        meterStaffAddDTO.setWater(0);
        meterStaffAddDTO.setMachineId(id.intValue());
        save(meterStaffAddDTO);
//        System.out.println(id);
    }


    /**
     * 输入年份月月份，返回此月的最大天数
     *
     * @param selectYear  年份
     * @param selectMonth 月份
     * @return
     */
    public static int getDaysOfMonth(Integer selectYear, Integer selectMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, selectYear);
        calendar.set(Calendar.MONTH, selectMonth);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 封装的获取机组下的水电热数据的方法
     *
     * @param meterDataDTOList dto数据集合
     * @param machineList      机组集合
     * @param type             数据类型：水/电/热
     * @return
     */
    private List<MeterDataDTO> getMachineMeterStaffData(List<MeterDataDTO> meterDataDTOList, List<HeatMachine> machineList, String type, Integer selectYear, Integer selectMonth) {
        //拼接数据过程
        for (HeatMachine machine : machineList) {
            MeterDataDTO meterDataDTO = new MeterDataDTO();
            meterDataDTO.setStationName(machine.getName());
            //获取该机组的水电热数据(当月的数据)
            QueryWrapper<MeterStaff> meterStaffQueryWrapper = new QueryWrapper<>();
            meterStaffQueryWrapper.lambda().eq(MeterStaff::getMachineId, machine.getId()).last("and YEAR(gmt_create) = " + selectYear + " and MONTH(gmt_create) = " + selectMonth);
            //该公司下的机组的水电热数据
            List<MeterStaff> meterStaffs = meterStaffMapper.selectList(meterStaffQueryWrapper);
            //dto中需要的数据集合
            List<MeterData> meterDataList = new ArrayList<>();

            //循环处理水电热数据，根据参数type判断取得是什么数据
            for (MeterStaff meterStaff : meterStaffs) {
                //格式化时间
                Date dNow = meterStaff.getGmtCreate();
                SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
                double data = 0;

                switch (type) {
                    case "水":
                        data = meterStaff.getWater();
                        break;
                    case "电":
                        data = meterStaff.getPower();
                        break;
                    case "热":
                        data = meterStaff.getHeat();
                        break;
                    default:
                }

                meterDataList.add(new MeterData(meterStaff.getId(), parseData2(data), ft.format(dNow)));
            }

            meterDataDTO.setMeterDataList(meterDataList);
            meterDataDTOList.add(meterDataDTO);
        }
        return meterDataDTOList;
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
        String ds = nf.format(d);
        return Double.valueOf(ds);
    }

    public static double parseData2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        String ds = df.format(d);
        return Double.valueOf(ds);
    }

}
