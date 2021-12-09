package com.hsgrjt.fushun.ihs.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


        for (HeatMachine heatMachine : machineList) {
            //拼接sql：根据时间倒序的最后一个截取，机组ID相对应
//            QueryWrapper<HeatNetworkData> heatNetworkDataQueryWrapper = new QueryWrapper<>();
//            heatNetworkDataQueryWrapper.lambda().orderByDesc(HeatNetworkData::getGmtCreate).last("limit 1").eq(HeatNetworkData::getStationId,heatMachine.getId());
//            HeatNetworkData data = mapper.selectOne(heatNetworkDataQueryWrapper);
            HeatNetworkData data = mapper.selectData(heatMachine.getId());

            HeatNetworkDataDTO dto = new HeatNetworkDataDTO();
            BeanUtils.copyProperties(data, dto);
            //set机组名称
            dto.setStationName(heatMachine.getName());
            //格式化时间
            Date dNow = data.getGmtCreate();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dto.setCreateDate(ft.format(dNow));
            dto = initData(dto);

            heatNetworkDataList.add(dto);
        }


        return R.ok("查询成功").putData(heatNetworkDataList);
    }


    /**
     * 格式化对象中的小数，在查询前调用
     *
     * @param dto 格式化前的对象
     * @return 格式化后的对象
     */
    public static HeatNetworkDataDTO initData(HeatNetworkDataDTO dto) {
        //一级网参数
        dto.setFirstSuTempature(parseData(dto.getFirstSuTempature(), 1)); //一网供温
        dto.setFirstReTempature(parseData(dto.getFirstReTempature(), 1));  //一网回温
        dto.setFirstFlow(parseData(dto.getFirstFlow(), 1));              //一网流量
        dto.setFirstSuPressure(parseData(dto.getFirstSuPressure(), 2));  //一网供压
        dto.setFirstRePressure(parseData(dto.getFirstRePressure(), 2));  //一网回压
        dto.setFirstFlowSet(parseData(dto.getFirstFlowSet(), 1));  //一网流量设定
        dto.setFirstConsumption(parseData(dto.getFirstConsumption(), 1));  //一网流量单耗
        dto.setFirstFlowSum(parseData(dto.getFirstFlowSum(), 1));  //一网流量累计
        dto.setFirstInstantHeat(parseData(dto.getFirstInstantHeat(), 1));  //一网热量
//        dto.setFirstHeatTotal(parseData(dto.getFirstHeatTotal(),1));  //一网热量累计

        //二级网参数
        dto.setSecSuTempature(parseData(dto.getSecSuTempature(), 1)); //二网供温
        dto.setSecReTempature(parseData(dto.getSecReTempature(), 1)); //二网回温
        dto.setSecFlow(parseData(dto.getSecFlow(), 1));               //二网流量
        dto.setSecSuPressure(parseData(dto.getSecSuPressure(), 2));  //二网供压
        dto.setSecRePressure(parseData(dto.getSecRePressure(), 2));  //二网回压
        dto.setSecFlowSum(parseData(dto.getSecFlowSum(), 1));  //二网流量累计

        //循环泵
        dto.setCirculatingPumpSet(parseData(dto.getCirculatingPumpSet(), 1));  //循环泵给定
        dto.setCirculatingPumpFeed(parseData(dto.getCirculatingPumpFeed(), 1));  //循环泵频率

        dto.setTargetTempature(parseData(dto.getTargetTempature(), 1));  //温度目标设定
        dto.setSecPressureMax(parseData(dto.getSecPressureMax(), 1));  //二网压力上限
        dto.setSecPressureSetMax(parseData(dto.getSecPressureSetMax(), 1));  //二网压力设定上限
        dto.setSecPressureSetMin(parseData(dto.getSecPressureSetMin(), 1));  //二网压力设定下限
        dto.setSecPressureMin(parseData(dto.getSecPressureMin(), 1));  //二网压力下限
        dto.setMainValveSet(parseData(dto.getMainValveSet(), 1));  //一阀给定
        dto.setMainValveFeed(parseData(dto.getMainValveFeed(), 1));  //一阀反馈
        dto.setAuxValveSet(parseData(dto.getAuxValveSet(), 1));  //二阀给定
        dto.setAuxValveFeed(parseData(dto.getAuxValveFeed(), 1));  //二阀反馈
        dto.setWaterTankLevel(parseData(dto.getWaterTankLevel(), 1));  //水箱水位
        dto.setWaterTankMinLevel(parseData(dto.getWaterTankMinLevel(), 1));  //水箱水位下限
        dto.setReplenishingPumpSet(parseData(dto.getReplenishingPumpSet(), 1));  //补水泵给定
        dto.setReplenishingPumpFeed(parseData(dto.getReplenishingPumpFeed(), 1));  //补水泵频率
        dto.setFirstHeatPredict(parseData(dto.getFirstHeatPredict(), 1));  //预测热量
        dto.setReplenishingFlow(parseData(dto.getReplenishingFlow(), 1));  //补水流量
//        dto.setReplenishingTotal(parseData(dto.getReplenishingTotal(),1));  //补水流量累计
        dto.setElectricPower(parseData(dto.getElectricPower(), 1));  //瞬时电量
        dto.setElectricTotal(parseData(dto.getElectricTotal(), 1));  //电量累计
        dto.setHeatConsumption(parseData(dto.getHeatConsumption(), 1));  //热负荷
        dto.setRoomTempatureAvg(parseData(dto.getRoomTempatureAvg(), 2));  //平均室温


        return dto;
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

        return Double.parseDouble(nf.format(d));
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
            for (int i = 1; i <= 24; i++) {
                List<HeatNetworkData> dataList = mapper.selectByTime(startYear, startMonth, startDay, i, machineId, 2);
                heatNetworkDataList.addAll(dataList);
            }
            return R.ok("查询成功").putData(heatNetworkDataList);
        } else if (diffDays > 0 & diffDays <= 7) {
            List<String> days = getDays(startTime, stopTime);
            for (String s : days) {
                String[] temp = s.split("-");
                int year = Integer.parseInt(temp[0]);
                int month = Integer.parseInt(temp[1]);
                int day = Integer.parseInt(temp[2]);
                for (int j = 1; j <= 24; j++) {
                    List<HeatNetworkData> dataList = mapper.selectByTime(year, month, day, j, machineId, 1);
                    heatNetworkDataList.addAll(dataList);
                }
            }
            return R.ok("查询成功").putData(heatNetworkDataList);
        } else {
            List<String> days = getDays(startTime, stopTime);
            for (String s : days) {
                String[] temp = s.split("-");
                int year = Integer.parseInt(temp[0]);
                int month = Integer.parseInt(temp[1]);
                int day = Integer.parseInt(temp[2]);
                for (int j = 1; j <= 24; j++) {
                    List<HeatNetworkData> dataList = mapper.selectByTime(year, month, day, j, machineId, 1);
                    heatNetworkDataList.addAll(dataList);
                }
            }
            return R.ok("查询成功").putData(heatNetworkDataList);
        }

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
