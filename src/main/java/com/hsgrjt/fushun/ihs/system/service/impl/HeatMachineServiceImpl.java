package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.mapper.HeatMachineMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  机组服务实现类
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-11
 */
@Service
public class HeatMachineServiceImpl extends ServiceImpl<HeatMachineMapper, HeatMachine> implements HeatMachineService {




    @Override
    public HeatMachine findById(Integer id) {
        return baseMapper.selectById(id);
    }

    /**
     * 获取用户所在公司的所有机组
     * @param user
     * @return
     */
    @Override
    public List<HeatMachine> getMachineByUser(User user) {

        QueryWrapper<HeatMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatMachine::getCompany,user.getAllowCompanys());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<String> getCenterStation(String company) {
        return baseMapper.getCenterStation(company);
    }

    @Override
    public List<HeatMachine> getMachineByCenterStation(String centerStation) {
        QueryWrapper<HeatMachine> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatMachine::getCenterStation,centerStation);
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 查询用户有管辖权的机组列表
     * @param ids user.getAllowMachines()字段
     * @return 机组list
     */
    @Override
    public List<HeatMachine> selectMachineByUser(String ids) {
        String[] temp = ids.split(",");
        List<HeatMachine> machineList = new ArrayList<>();
        for (String s : temp) {
            machineList.add(baseMapper.selectById(s));
        }
        return machineList;
    }

}
