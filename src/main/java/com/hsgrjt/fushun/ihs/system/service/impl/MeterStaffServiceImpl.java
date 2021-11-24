package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.*;
import com.hsgrjt.fushun.ihs.system.entity.dto.DayFormDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    @Override
    public void save(MeterStaffAddDTO dto) {
        MeterStaff entity = new MeterStaff();
        BeanUtils.copyProperties(dto, entity);
        entity.setGmtCreate(new Date());

        HeatMachine machine = machineService.findById(dto.getMachineId());
        entity.setMachineName(machine.getName());
        entity.setCenterStation(machine.getCenterStation());

        meterStaffMapper.insert(entity);
    }

    @Override
    public R<List<MeterDataDTO>> findAll(User user, String type) {
        //获取用户所在公司下的机组列表
        List<HeatMachine> machineList = machineService.getMachineByUser(user);

        //该方法返回的数据dto
        List<MeterDataDTO> meterDataDTOList = new ArrayList<>();

        //调用方法，传入dto集合，机组集合，数据类型，即可获得这些机组下的某种类型的数据
        getMachineMeterStaffData(meterDataDTOList, machineList, type);

        return R.ok("查询成功").putData(meterDataDTOList);
    }



    /**
     * 查看日报表
     *
     * @param user
     * @return
     */
    @Override
    public R<List<DayFormDTO>> getDayFromWater(User user) {
        int maxDays = getDaysOfMonth(new Date());    //本月最大天数
        List<DayFormDTO> dayFormDTOList = new ArrayList<>();
        List<String> centerStations = machineService.getCenterStation(user.getAllowCompanys());

        DayFormDTO xiaojiDayForm = new DayFormDTO();      //小记对象
        xiaojiDayForm.setStationName("小记");
        List<MeterData> xiaojiList = new ArrayList<>();

        //循环遍历中心站，最多也就1-2次
        for (String centerStation : centerStations) {
            //这个list是拿到的当前中心站下面的机组列表
            List<HeatMachine> machineList = machineService.getMachineByCenterStation(centerStation);

            //这个是需要set进dayReport中的list,这个list中的对象存的就是具体的数据
            List<MeterDataDTO> meterDataDTOList = new ArrayList<>();

            //获得机组的水电热数据
            getMachineMeterStaffData(meterDataDTOList, machineList, "水");

            //计算数据过程---只计算基础数据和合计等单机组合计数据
            for (int i = 0; i < meterDataDTOList.size(); i++) {
                System.out.println("\033[31;4m" + meterDataDTOList.get(i).toString() + "\033[0m");
                double bigSum = 0;
                String stationName = meterDataDTOList.get(i).getStationName();
                List<MeterData> sourceData = meterDataDTOList.get(i).getMeterDataList();
                List<MeterData> targetdata = new ArrayList<>();
                for (int j = 0; j < maxDays; j++) {
                    MeterData meterData = new MeterData();
                    //避免月底的情况，数组越界。月底单独获取，单独计算
                    if (j < sourceData.size() - 1) {
                        meterData.setMeterTime(sourceData.get(j).getMeterTime());
                        double data = sourceData.get(j + 1).getMeterData() - sourceData.get(j).getMeterData();
                        meterData.setMeterData(data);
                        bigSum += data;
                        targetdata.add(meterData);
                    } else {
                        double data = 0;
                        meterData.setMeterData(data);
                        bigSum += data;
                        targetdata.add(meterData);
                    }
                }

                DayFormDTO dayFormDTO = new DayFormDTO();
                dayFormDTO.setCenterStation(centerStation);
                dayFormDTO.setStationName(stationName);
                dayFormDTO.setBigSum(bigSum);
                dayFormDTO.setMeterDataList(targetdata);
                dayFormDTOList.add(dayFormDTO);
            }
        }


        //计算数据过程---计算小记、结余等多机组合计数据
        for (int i = 0; i < maxDays; i++) {
            MeterData meterData = new MeterData();
            double smallSum = 0;
            for (int j = 0; j < dayFormDTOList.size(); j++) {
                    smallSum += dayFormDTOList.get(j).getMeterDataList().get(i).getMeterData();
            }
            meterData.setMeterData(smallSum);
            xiaojiList.add(meterData);
        }

        xiaojiDayForm.setMeterDataList(xiaojiList);
        xiaojiDayForm.setBigSum(xiaojiList.stream().mapToDouble(MeterData::getMeterData).sum());
        dayFormDTOList.add(xiaojiDayForm);


        return R.ok("查询成功").putData(dayFormDTOList);
    }

    @Override
    public void initDataEveryDay() {
        User user1 = new User();
        user1.setAllowCompanys("城南热电");
        List<HeatMachine> machineList1 = machineService.getMachineByUser(user1);

        User user2 = new User();
        user1.setAllowCompanys("城南热电");
        List<HeatMachine> machineList2 = machineService.getMachineByUser(user2);

        User user3 = new User();
        user1.setAllowCompanys("城南热电");
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

    private void initData(Long id) {
        MeterStaffAddDTO meterStaffAddDTO = new MeterStaffAddDTO();
        meterStaffAddDTO.setHeat(0);
        meterStaffAddDTO.setPower(0);
        meterStaffAddDTO.setWater(0);

        meterStaffAddDTO.setMachineId(id.intValue());
        save(meterStaffAddDTO);
    }


    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
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
    private List<MeterDataDTO> getMachineMeterStaffData(List<MeterDataDTO> meterDataDTOList, List<HeatMachine> machineList, String type) {
        //拼接数据过程
        for (HeatMachine machine : machineList) {
            MeterDataDTO meterDataDTO = new MeterDataDTO();
            meterDataDTO.setStationName(machine.getName());
            //获取该机组的水电热数据(当月的数据)
            QueryWrapper<MeterStaff> meterStaffQueryWrapper = new QueryWrapper<>();
            meterStaffQueryWrapper.lambda().eq(MeterStaff::getMachineId, machine.getId()).last("and date_format( gmt_create, '%Y%m' ) = date_format(curdate( ) , '%Y%m' )");
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

                meterDataList.add(new MeterData(data, ft.format(dNow)));
            }

            meterDataDTO.setMeterDataList(meterDataList);
            meterDataDTOList.add(meterDataDTO);
        }
        return meterDataDTOList;
    }


}
