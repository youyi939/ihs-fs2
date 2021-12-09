package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 机组数据service
 * @Date: Create in  2021/10/17 下午4:17
 */
@Service
public interface HeatNetworkDataService {


    /**
     * 早期测试接口
     * @param start
     * @param end
     * @return
     */
    R<List<HeatNetworkData>> selectByTime(Data start,Data end);


    /**
     * 早期测试接口
     * @param page
     * @param machineName
     * @return
     */
    R<IPage<HeatNetworkDataDTO>> findAll(Page<HeatNetworkData> page, String machineName);

    /**
     * 添加机组数据
     * @param data
     * @return
     */
    R<HeatNetworkData> add(HeatNetworkData data);

    /**
     * 查询实时数据
     * @return 返回单条数据
     */
    R<HeatNetworkDataDTO> selectByRealTime(String company);


    /**
     * 查询某个机组的历史数据（暂定查询最新时间往下50条记录）
     * @return 机组数据集合
     */
    R<List<HeatNetworkDataDTO>> selectHeatDataHistory(Integer stationId);


    /**
     * 查询某个机组的历史数据（时间/机组纬度筛选）
     * @param machineId 机组id
     * @param startTime 开始时间
     * @param stopTime 结束时间
     * @return
     */
    R<List<HeatNetworkDataDTO>> selectHeatDataHistoryForLine(Integer machineId,String startTime,String stopTime) throws ParseException;

}
