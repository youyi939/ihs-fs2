package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.*;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.HeatMachineMapper;
import com.hsgrjt.fushun.ihs.system.mapper.MeterStaffMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import com.hsgrjt.fushun.ihs.system.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    HeatMachineMapper machineMapper;

    @Override
    public void save(MeterStaffAddDTO dto) {
        MeterStaff entity = new MeterStaff();
        BeanUtils.copyProperties(dto,entity);
        entity.setGmtCreate(new Date());

        HeatMachine machine = machineService.findById(dto.getMachineId());
        entity.setMachineName(machine.getName());
        entity.setCenterStation(machine.getCenterStation());

        meterStaffMapper.insert(entity);
    }

    @Override
    public R<List<MeterDataDTO>> findAll(User user,String type) {
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
     * @param user
     * @return
     */
    @Override
    public R<List<DayReport>> getDayFromWater(User user) {
        //查询当前公司下共有几个中心站






        return null;
    }


    /**
     * 封装的获取机组下的水电热数据的方法
     * @param meterDataDTOList dto数据集合
     * @param machineList 机组集合
     * @param type 数据类型：水/电/热
     * @return
     */
    private List<MeterDataDTO> getMachineMeterStaffData(List<MeterDataDTO> meterDataDTOList,List<HeatMachine> machineList,String type){
        //拼接数据过程
        for (HeatMachine machine: machineList) {
            MeterDataDTO meterDataDTO = new MeterDataDTO();
            meterDataDTO.setStationName(machine.getName());
            //获取该机组的水电热数据(当月的数据)
            QueryWrapper<MeterStaff> meterStaffQueryWrapper = new QueryWrapper<>();
            meterStaffQueryWrapper.lambda().eq(MeterStaff::getMachineId,machine.getId()).last("and date_format( gmt_create, '%Y%m' ) = date_format(curdate( ) , '%Y%m' )");
            //该公司下的机组的水电热数据
            List<MeterStaff> meterStaffs = meterStaffMapper.selectList(meterStaffQueryWrapper);
            //dto中需要的数据集合
            List<MeterData> meterDataList = new ArrayList<>();

            //循环处理水电热数据，根据参数type判断取得是什么数据
            for (MeterStaff meterStaff: meterStaffs) {
                //格式化时间
                Date dNow = meterStaff.getGmtCreate();
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
                double data = 0;

                switch (type){
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

                meterDataList.add(new MeterData(data,ft.format(dNow)));
            }

            meterDataDTO.setMeterDataList(meterDataList);
            meterDataDTOList.add(meterDataDTO);
        }
        return meterDataDTOList;
    }


}
