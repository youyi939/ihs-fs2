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
import java.math.RoundingMode;
import java.text.NumberFormat;
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
            dto = initData(dto);

            heatNetworkDataList.add(dto);
        }


        return R.ok("查询成功").putData(heatNetworkDataList);
    }


    /**
     * 格式化对象中的小数，在查询前调用
     * @param dto 格式化前的对象
     * @return 格式化后的对象
     */
    public static HeatNetworkDataDTO initData(HeatNetworkDataDTO dto){
        //一级网参数
        dto.setFirstSuTempature(parseData(dto.getFirstSuTempature(),1)); //一网供温
        dto.setFirstReTempature(parseData(dto.getFirstReTempature(),1));  //一网回温
        dto.setFirstFlow(parseData(dto.getFirstFlow(),1));              //一网流量
        dto.setFirstSuPressure(parseData(dto.getFirstSuPressure(),2));  //一网供压
        dto.setFirstRePressure(parseData(dto.getFirstRePressure(),2));  //一网回压
        dto.setFirstFlowSet(parseData(dto.getFirstFlowSet(),1));  //一网流量设定
        dto.setFirstConsumption(parseData(dto.getFirstConsumption(),1));  //一网流量单耗
        dto.setFirstFlowSum(parseData(dto.getFirstFlowSum(),1));  //一网流量累计
        dto.setFirstInstantHeat(parseData(dto.getFirstInstantHeat(),1));  //一网热量
//        dto.setFirstHeatTotal(parseData(dto.getFirstHeatTotal(),1));  //一网热量累计

        //二级网参数
        dto.setSecSuTempature(parseData(dto.getSecSuTempature(),1)); //二网供温
        dto.setSecReTempature(parseData(dto.getSecReTempature(),1)); //二网回温
        dto.setSecFlow(parseData(dto.getSecFlow(),1));               //二网流量
        dto.setSecSuPressure(parseData(dto.getSecSuPressure(),2));  //二网供压
        dto.setSecRePressure(parseData(dto.getSecRePressure(),2));  //二网回压
        dto.setSecFlowSum(parseData(dto.getSecFlowSum(),1));  //二网流量累计

        //循环泵
        dto.setCirculatingPumpSet(parseData(dto.getCirculatingPumpSet(),1));  //循环泵给定
        dto.setCirculatingPumpFeed(parseData(dto.getCirculatingPumpFeed(),1));  //循环泵频率

        dto.setTargetTempature(parseData(dto.getTargetTempature(),1));  //温度目标设定
        dto.setSecPressureMax(parseData(dto.getSecPressureMax(),1));  //二网压力上限
        dto.setSecPressureSetMax(parseData(dto.getSecPressureSetMax(),1));  //二网压力设定上限
        dto.setSecPressureSetMin(parseData(dto.getSecPressureSetMin(),1));  //二网压力设定下限
        dto.setSecPressureMin(parseData(dto.getSecPressureMin(),1));  //二网压力下限
        dto.setMainValveSet(parseData(dto.getMainValveSet(),1));  //一阀给定
        dto.setMainValveFeed(parseData(dto.getMainValveFeed(),1));  //一阀反馈
        dto.setAuxValveSet(parseData(dto.getAuxValveSet(),1));  //二阀给定
        dto.setAuxValveFeed(parseData(dto.getAuxValveFeed(),1));  //二阀反馈
        dto.setWaterTankLevel(parseData(dto.getWaterTankLevel(),1));  //水箱水位
        dto.setWaterTankMinLevel(parseData(dto.getWaterTankMinLevel(),1));  //水箱水位下限
        dto.setReplenishingPumpSet(parseData(dto.getReplenishingPumpSet(),1));  //补水泵给定
        dto.setReplenishingPumpFeed(parseData(dto.getReplenishingPumpFeed(),1));  //补水泵频率
        dto.setFirstHeatPredict(parseData(dto.getFirstHeatPredict(),1));  //预测热量
        dto.setReplenishingFlow(parseData(dto.getReplenishingFlow(),1));  //补水流量
//        dto.setReplenishingTotal(parseData(dto.getReplenishingTotal(),1));  //补水流量累计
        dto.setElectricPower(parseData(dto.getElectricPower(),1));  //瞬时电量
        dto.setElectricTotal(parseData(dto.getElectricTotal(),1));  //电量累计
        dto.setHeatConsumption(parseData(dto.getHeatConsumption(),1));  //热负荷
        dto.setRoomTempatureAvg(parseData(dto.getRoomTempatureAvg(),2));  //平均室温


        return dto;
    }

    /**
     * 保留小数点后几位的方法
     * @param d 小数
     * @param num 保留到小数点后几位
     * @return 保留后的小数
     */
    private static double parseData(double d, int num){

        NumberFormat nf = NumberFormat.getNumberInstance();
// 保留两位小数
        nf.setMaximumFractionDigits(num);
// 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        return Double.parseDouble(nf.format(d));
    }


}
