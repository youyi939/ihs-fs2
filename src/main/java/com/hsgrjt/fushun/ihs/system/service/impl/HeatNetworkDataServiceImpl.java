package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.Plan;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.HeatMachineMapper;
import com.hsgrjt.fushun.ihs.system.mapper.HeatNetworkDataMapper;
import com.hsgrjt.fushun.ihs.system.service.HeatNetworkDataService;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.utils.V;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.math.RoundingMode;
import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
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

    @Autowired
    PlanService planService;

    @Override
    public R<List<HeatNetworkData>> selectByTime(Data start, Data end) {
        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().between(HeatNetworkData::getGmtCreate, start, end);
        return R.ok("查询成功").putData(mapper.selectList(queryWrapper));
    }

    @Override
    public R<IPage<HeatNetworkDataDTO>> findAll(Page<HeatNetworkData> page, String machineName) {
        //根据机组名查询机组对象
        QueryWrapper<HeatMachine> machineQueryWrapper = new QueryWrapper<>();
        machineQueryWrapper.lambda().eq(HeatMachine::getName, machineName);
        HeatMachine heatMachine = machineMapper.selectOne(machineQueryWrapper);

        //根据机组对象中的id去机组数据表中查询
        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatNetworkData::getStationId, heatMachine.getId());
        List<HeatNetworkData> dataList = mapper.selectPage(page, queryWrapper).getRecords();

        List<HeatNetworkDataDTO> dataDTOList = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(dataList.get(i), dto);
            Date dNow = dataList.get(i).getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
        //获取当前公司的机组数据
        QueryWrapper<HeatMachine> machineQueryWrapper = new QueryWrapper<>();
        machineQueryWrapper.lambda().eq(HeatMachine::getCompany, company);

        //获取机组list
        List<HeatMachine> machineList = machineMapper.selectList(machineQueryWrapper);
        //dto的list
        List<HeatNetworkDataDTO> heatNetworkDataList = new ArrayList<>();

//        所有机组的最新数据
        List<HeatNetworkData> networkDataList = mapper.selectDataList();
        //已选机组的list
        List<HeatNetworkData> dataList = new ArrayList<>();             

//        筛选机组
        for (HeatNetworkData heatNetworkData : networkDataList) {
            for (HeatMachine heatMachine : machineList) {
                if (heatNetworkData.getStationId() == heatMachine.getId().intValue()) {
                    dataList.add(heatNetworkData);
                }
            }
        }


        for (HeatMachine heatMachine : machineList) {
            HeatNetworkData data = null;
            for (HeatNetworkData heatNetworkData : dataList) {
                if (heatMachine.getId().intValue() == heatNetworkData.getStationId()) {
                    data = heatNetworkData;
                }
            }
            getData(heatMachine, heatNetworkDataList,data);
        }


        return R.ok("查询成功").putData(heatNetworkDataList);
    }

    @Async
    public void getData(HeatMachine heatMachine, List<HeatNetworkDataDTO> heatNetworkDataList,HeatNetworkData data) {
        Plan plan = planService.selectByStationName(heatMachine.getName(), heatMachine.getId().intValue());

        if (!V.isEmpty(data)) {
            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(data, dto);
            //set机组名称
            dto.setStationName(heatMachine.getName());
            if (V.isEmpty(plan)){
                dto.setSupplyArea(0);
            }else {
                dto.setSupplyArea(plan.getArea());
            }

            //格式化时间
            Date dNow = data.getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setCreateDate(ft.format(dNow));
            dto = initData(dto);

            heatNetworkDataList.add(dto);
        }
    }


    /**
     * 格式化对象中的小数，在查询前调用
     *
     * @param dto 格式化前的对象
     * @return 格式化后的对象
     */
    public static HeatNetworkDataDTO initData(HeatNetworkDataDTO dto) {
        //一级网参数
        dto.setFirstSuTempature(parseData2(dto.getFirstSuTempature())); //一网供温
        dto.setFirstReTempature(parseData2(dto.getFirstReTempature()));  //一网回温
        dto.setFirstFlow(parseData2(dto.getFirstFlow()));              //一网流量
        dto.setFirstSuPressure(parseData2(dto.getFirstSuPressure()));  //一网供压
        dto.setFirstRePressure(parseData2(dto.getFirstRePressure()));  //一网回压
        dto.setFirstFlowSet(parseData2(dto.getFirstFlowSet()));  //一网流量设定
        dto.setFirstConsumption(parseData2(dto.getFirstConsumption()));  //一网流量单耗
        dto.setFirstFlowSum(parseData2(dto.getFirstFlowSum()));  //一网流量累计
        dto.setFirstInstantHeat(parseData2(dto.getFirstInstantHeat()));  //一网热量
//        dto.setFirstHeatTotal(parseData(dto.getFirstHeatTotal(),1));  //一网热量累计

        //二级网参数
        dto.setSecSuTempature(parseData2(dto.getSecSuTempature())); //二网供温
        dto.setSecReTempature(parseData2(dto.getSecReTempature())); //二网回温
        dto.setSecFlow(parseData2(dto.getSecFlow()));               //二网流量
        dto.setSecSuPressure(parseData2(dto.getSecSuPressure()));  //二网供压
        dto.setSecRePressure(parseData2(dto.getSecRePressure()));  //二网回压
        dto.setSecFlowSum(parseData2(dto.getSecFlowSum()));  //二网流量累计

        //循环泵
        dto.setCirculatingPumpSet(parseData2(dto.getCirculatingPumpSet()));  //循环泵给定
        dto.setCirculatingPumpFeed(parseData2(dto.getCirculatingPumpFeed()));  //循环泵频率

        dto.setTargetTempature(parseData2(dto.getTargetTempature()));  //温度目标设定
        dto.setSecPressureMax(parseData2(dto.getSecPressureMax()));  //二网压力上限
        dto.setSecPressureSetMax(parseData2(dto.getSecPressureSetMax()));  //二网压力设定上限
        dto.setSecPressureSetMin(parseData2(dto.getSecPressureSetMin()));  //二网压力设定下限
        dto.setSecPressureMin(parseData2(dto.getSecPressureMin()));  //二网压力下限
        dto.setMainValveSet(parseData2(dto.getMainValveSet()));  //一阀给定
        dto.setMainValveFeed(parseData2(dto.getMainValveFeed()));  //一阀反馈
        dto.setAuxValveSet(parseData2(dto.getAuxValveSet()));  //二阀给定
        dto.setAuxValveFeed(parseData2(dto.getAuxValveFeed()));  //二阀反馈
        dto.setWaterTankLevel(parseData2(dto.getWaterTankLevel()));  //水箱水位
        dto.setWaterTankMinLevel(parseData2(dto.getWaterTankMinLevel()));  //水箱水位下限
        dto.setReplenishingPumpSet(parseData2(dto.getReplenishingPumpSet()));  //补水泵给定
        dto.setReplenishingPumpFeed(parseData2(dto.getReplenishingPumpFeed()));  //补水泵频率
        dto.setFirstHeatPredict(parseData2(dto.getFirstHeatPredict()));  //预测热量
        dto.setReplenishingFlow(parseData2(dto.getReplenishingFlow()));  //补水流量
//        dto.setReplenishingTotal(parseData(dto.getReplenishingTotal(),1));  //补水流量累计
        dto.setElectricPower(parseData2(dto.getElectricPower()));  //瞬时电量
        dto.setElectricTotal(parseData2(dto.getElectricTotal()));  //电量累计
        dto.setHeatConsumption(parseData2(dto.getHeatConsumption()));  //热负荷
        dto.setRoomTempatureAvg(parseData2(dto.getRoomTempatureAvg()));  //平均室温


        return dto;
    }


    public static double parseData2(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        String ds = df.format(d);
        return Double.valueOf(ds);
    }

    /**
     * 查询当前机组的近五十条记录，用于折线图
     *
     * @param stationId 机组id
     * @return 机组dto的集合
     */
    @Override
    public R<List<HeatNetworkDataDTO>> selectHeatDataHistory(Integer stationId) {
        HeatMachine machine = machineMapper.selectById(stationId);

        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatNetworkData::getStationId, stationId).orderByDesc(HeatNetworkData::getGmtCreate).last("limit 50");
        List<HeatNetworkData> dataList = mapper.selectList(queryWrapper);
        List<HeatNetworkDataDTO> dataDTOList = new ArrayList<>();
        for (HeatNetworkData data : dataList) {
            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(data, dto);
            dto.setStationName(machine.getName());
            //格式化时间
            Date dNow = data.getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            dto.setCreateDate(ft.format(dNow.getTime()));
            dto = initData(dto);
            dataDTOList.add(dto);
        }

        return R.ok("查询成功").putData(dataDTOList);
    }

    @Override
    public R selectHeatDataHistoryForLine(Integer machineId, String startTime, String stopTime) throws ParseException {

        String[] temp1 = startTime.split("-");
        String[] temp2 = stopTime.split("-");

        //起始年月日时间
        int startYear = Integer.parseInt(temp1[0]);
        int startMonth = Integer.parseInt(temp1[1]);
        int startDay = Integer.parseInt(temp1[2]);

        //结束年月日时间
        int stopYear = Integer.parseInt(temp2[0]);
        int stopMonth = Integer.parseInt(temp2[1]);
        int stopDay = Integer.parseInt(temp2[2]);

        int diffDays = getDiffDays(startTime, stopTime);

        List<HeatNetworkData> heatNetworkDataList = new ArrayList<>();


        if (diffDays == 0) {
            List<HeatNetworkData> dataList = mapper.selectByTimeOneDay(startYear, startMonth, startDay, machineId);
            heatNetworkDataList.addAll(dataList);
            return getR(heatNetworkDataList);
        } else if (diffDays > 0 & diffDays <= 7) {
            List<String> days = getDays(startTime, stopTime);
            for (String s : days) {
                String[] temp = s.split("-");
                int year = Integer.parseInt(temp[0]);
                int month = Integer.parseInt(temp[1]);
                int day = Integer.parseInt(temp[2]);

                List<HeatNetworkData> dataList = mapper.selectByTimeOneDay(year, month, day, machineId);
                heatNetworkDataList.addAll(dataList);
            }
            return getR(heatNetworkDataList);
        } else {
            List<String> days = getDays(startTime, stopTime);
            for (String s : days) {
                String[] temp = s.split("-");
                int year = Integer.parseInt(temp[0]);
                int month = Integer.parseInt(temp[1]);
                int day = Integer.parseInt(temp[2]);
                List<HeatNetworkData> dataList = mapper.selectByTimeOneDay(year, month, day, machineId);
                heatNetworkDataList.addAll(dataList);
            }
            return getR(heatNetworkDataList);
        }

    }


    private R getR(List<HeatNetworkData> heatNetworkDataList) {
        for (int i = 0; i < heatNetworkDataList.size(); i++) {
            //一级网参数
            heatNetworkDataList.get(i).setFirstSuTempature(parseData2(heatNetworkDataList.get(i).getFirstSuTempature())); //一网供温
            heatNetworkDataList.get(i).setFirstReTempature(parseData2(heatNetworkDataList.get(i).getFirstReTempature()));  //一网回温
            heatNetworkDataList.get(i).setFirstFlow(parseData2(heatNetworkDataList.get(i).getFirstFlow()));              //一网流量
            heatNetworkDataList.get(i).setFirstSuPressure(parseData2(heatNetworkDataList.get(i).getFirstSuPressure()));  //一网供压
            heatNetworkDataList.get(i).setFirstRePressure(parseData2(heatNetworkDataList.get(i).getFirstRePressure()));  //一网回压
            heatNetworkDataList.get(i).setFirstFlowSet(parseData2(heatNetworkDataList.get(i).getFirstFlowSet()));  //一网流量设定
            heatNetworkDataList.get(i).setFirstConsumption(parseData2(heatNetworkDataList.get(i).getFirstConsumption()));  //一网流量单耗
            heatNetworkDataList.get(i).setFirstFlowSum(parseData2(heatNetworkDataList.get(i).getFirstFlowSum()));  //一网流量累计
            heatNetworkDataList.get(i).setFirstInstantHeat(parseData2(heatNetworkDataList.get(i).getFirstInstantHeat()));  //一网热量
//        dto.setFirstHeatTotal(parseData(dto.getFirstHeatTotal(),1));  //一网热量累计

            //二级网参数
            heatNetworkDataList.get(i).setSecSuTempature(parseData2(heatNetworkDataList.get(i).getSecSuTempature())); //二网供温
            heatNetworkDataList.get(i).setSecReTempature(parseData2(heatNetworkDataList.get(i).getSecReTempature())); //二网回温
            heatNetworkDataList.get(i).setSecFlow(parseData2(heatNetworkDataList.get(i).getSecFlow()));               //二网流量
            heatNetworkDataList.get(i).setSecSuPressure(parseData2(heatNetworkDataList.get(i).getSecSuPressure()));  //二网供压
            heatNetworkDataList.get(i).setSecRePressure(parseData2(heatNetworkDataList.get(i).getSecRePressure()));  //二网回压
            heatNetworkDataList.get(i).setSecFlowSum(parseData2(heatNetworkDataList.get(i).getSecFlowSum()));  //二网流量累计

            //循环泵
            heatNetworkDataList.get(i).setCirculatingPumpSet(parseData2(heatNetworkDataList.get(i).getCirculatingPumpSet()));  //循环泵给定
            heatNetworkDataList.get(i).setCirculatingPumpFeed(parseData2(heatNetworkDataList.get(i).getCirculatingPumpFeed()));  //循环泵频率

            heatNetworkDataList.get(i).setTargetTempature(parseData2(heatNetworkDataList.get(i).getTargetTempature()));  //温度目标设定
            heatNetworkDataList.get(i).setSecPressureMax(parseData2(heatNetworkDataList.get(i).getSecPressureMax()));  //二网压力上限
            heatNetworkDataList.get(i).setSecPressureSetMax(parseData2(heatNetworkDataList.get(i).getSecPressureSetMax()));  //二网压力设定上限
            heatNetworkDataList.get(i).setSecPressureSetMin(parseData2(heatNetworkDataList.get(i).getSecPressureSetMin()));  //二网压力设定下限
            heatNetworkDataList.get(i).setSecPressureMin(parseData2(heatNetworkDataList.get(i).getSecPressureMin()));  //二网压力下限
            heatNetworkDataList.get(i).setMainValveSet(parseData2(heatNetworkDataList.get(i).getMainValveSet()));  //一阀给定
            heatNetworkDataList.get(i).setMainValveFeed(parseData2(heatNetworkDataList.get(i).getMainValveFeed()));  //一阀反馈
            heatNetworkDataList.get(i).setAuxValveSet(parseData2(heatNetworkDataList.get(i).getAuxValveSet()));  //二阀给定
            heatNetworkDataList.get(i).setAuxValveFeed(parseData2(heatNetworkDataList.get(i).getAuxValveFeed()));  //二阀反馈
            heatNetworkDataList.get(i).setWaterTankLevel(parseData2(heatNetworkDataList.get(i).getWaterTankLevel()));  //水箱水位
            heatNetworkDataList.get(i).setWaterTankMinLevel(parseData2(heatNetworkDataList.get(i).getWaterTankMinLevel()));  //水箱水位下限
            heatNetworkDataList.get(i).setReplenishingPumpSet(parseData2(heatNetworkDataList.get(i).getReplenishingPumpSet()));  //补水泵给定
            heatNetworkDataList.get(i).setReplenishingPumpFeed(parseData2(heatNetworkDataList.get(i).getReplenishingPumpFeed()));  //补水泵频率
            heatNetworkDataList.get(i).setFirstHeatPredict(parseData2(heatNetworkDataList.get(i).getFirstHeatPredict()));  //预测热量
            heatNetworkDataList.get(i).setReplenishingFlow(parseData2(heatNetworkDataList.get(i).getReplenishingFlow()));  //补水流量
//        dto.setReplenishingTotal(parseData(dto.getReplenishingTotal(),1));  //补水流量累计
            heatNetworkDataList.get(i).setElectricPower(parseData2(heatNetworkDataList.get(i).getElectricPower()));  //瞬时电量
            heatNetworkDataList.get(i).setElectricTotal(parseData2(heatNetworkDataList.get(i).getElectricTotal()));  //电量累计
            heatNetworkDataList.get(i).setHeatConsumption(parseData2(heatNetworkDataList.get(i).getHeatConsumption()));  //热负荷
            heatNetworkDataList.get(i).setRoomTempatureAvg(parseData2(heatNetworkDataList.get(i).getRoomTempatureAvg()));  //平均室温
        }
        return R.ok("查询成功").putData(heatNetworkDataList);
    }

    private static int getDiffDays(String startTime, String stopTime) throws ParseException {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*天数差*/
        Date fromDate1 = simpleFormat.parse(startTime);
        Date toDate1 = simpleFormat.parse(stopTime);
        long from1 = fromDate1.getTime();
        long to1 = toDate1.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        return days;
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
