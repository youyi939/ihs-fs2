package com.hsgrjt.fushun.ihs.system.service.impl;

import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
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
    public List<HeatMachine> selectMachineByUser(String ids) {
        String[] temp = ids.split(",");
        List<HeatMachine> machineList = new ArrayList<>();
        for (String s : temp) {
            machineList.add(baseMapper.selectById(s));
        }
        return machineList;
    }

}
