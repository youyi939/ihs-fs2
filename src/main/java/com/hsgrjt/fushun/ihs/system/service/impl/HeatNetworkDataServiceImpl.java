package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.IhsFile;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.HeatMachineMapper;
import com.hsgrjt.fushun.ihs.system.mapper.HeatNetworkDataMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatNetworkDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/10/17 下午4:17
 */
@Service
public class HeatNetworkDataServiceImpl implements HeatNetworkDataService {

    @Autowired
    HeatNetworkDataMapper mapper;

    @Autowired
    HeatMachineMapper machineMapper;

    @Override
    public R<List<HeatNetworkData>> selectByTime(Data start, Data end) {
        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(HeatNetworkData::getGmtCreate,start,end);
        return R.ok("查询成功").putData(mapper.selectList(queryWrapper));
    }

    @Override
    public R<IPage<HeatNetworkDataDTO>> findAll(Page<HeatNetworkData> page, String machineName) {
        //根据机组名查询机组对象
        QueryWrapper<HeatMachine> machineQueryWrapper = new QueryWrapper<>();
        machineQueryWrapper.lambda().eq(HeatMachine::getName,machineName);
        HeatMachine heatMachine = machineMapper.selectOne(machineQueryWrapper);

        //根据机组对象中的id去机组数据表中查询
        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatNetworkData::getStationId,heatMachine.getId());
        List<HeatNetworkData> dataList = mapper.selectPage(page,queryWrapper).getRecords();


        List<HeatNetworkDataDTO> dataDTOList = new ArrayList<>();


        for (int i = 0; i < dataList.size() ; i++) {
            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(dataList.get(i),dto);
            Date dNow = dataList.get(i).getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            dto.setCreateDate(ft.format(dNow));

            HeatMachine machine = machineMapper.selectById(dataList.get(i).getStationId());
            dto.setStationName(machine.getName());
            dataDTOList.add(dto);
        }


        IPage<HeatNetworkDataDTO> dtoiPage = new Page<>();
        dtoiPage.setRecords(dataDTOList);
        dtoiPage.setTotal(page.getTotal());

        return R.ok("查询成功").putData(dtoiPage);
    }

    @Override
    public R<HeatNetworkData> add(HeatNetworkData data) {
        data.setGmtCreate(new Date());
        mapper.insert(data);
        return R.ok("新增成功").putData(data);
    }

    @Override
    public R<HeatNetworkDataDTO> selectByRealTime(String company) {
        // TODO: 2021/11/8 先查询当前公司的机组列表，然后在机组数据表中筛选当前机组列表中的数据。然后创建时间倒叙，第一个

        //获取当前公司的机组数据
        QueryWrapper<HeatMachine> machineQueryWrapper = new QueryWrapper<>();
        machineQueryWrapper.lambda().eq(HeatMachine::getCompany,company);
        List<HeatMachine>  machineList = machineMapper.selectList(machineQueryWrapper);

        List<HeatNetworkDataDTO> heatNetworkDataList = new ArrayList<>();


        for (HeatMachine heatMachine : machineList) {
            System.out.println(heatMachine.getId());
            QueryWrapper<HeatNetworkData> heatNetworkDataQueryWrapper = new QueryWrapper<>();
            heatNetworkDataQueryWrapper.lambda().orderByDesc(HeatNetworkData::getGmtCreate).last("limit 1").eq(HeatNetworkData::getStationId,heatMachine.getId());
            HeatNetworkData data = mapper.selectOne(heatNetworkDataQueryWrapper);
            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(data,dto);
            //set机组名称
            dto.setStationName(heatMachine.getName());
            //格式化时间
            Date dNow = data.getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
            dto.setCreateDate(ft.format(dNow));
            heatNetworkDataList.add(dto);
        }


        return R.ok("查询成功").putData(heatNetworkDataList);
    }


}
