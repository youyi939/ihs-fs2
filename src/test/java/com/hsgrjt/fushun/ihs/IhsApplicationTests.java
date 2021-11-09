package com.hsgrjt.fushun.ihs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.mapper.HeatNetworkDataMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class IhsApplicationTests {

    @Autowired
    HeatNetworkDataMapper mapper;

    @Test
    void contextLoads() {
        Integer stationId = 1;
        QueryWrapper<HeatNetworkData> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HeatNetworkData::getStationId,stationId).orderByDesc(HeatNetworkData::getGmtCreate).last("limit 50");
        List<HeatNetworkData> list = mapper.selectList(queryWrapper);
        System.out.println(list.size());
    }

}
