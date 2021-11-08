package com.hsgrjt.fushun.ihs.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 机组数据service
 * @Date: Create in  2021/10/17 下午4:17
 */
@Service
public interface HeatNetworkDataService {


    R<List<HeatNetworkData>> selectByTime(Data start,Data end);


    R<IPage<HeatNetworkDataDTO>> findAll(Page<HeatNetworkData> page, String machineName);

    R<HeatNetworkData> add(HeatNetworkData data);

    /**
     * 查询实时数据
     * @return 返回单条数据
     */
    R<HeatNetworkDataDTO> selectByRealTime(String company);

}
