package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.HeatNetworkDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/10/17 下午4:26
 */
@Api(tags = {"CORE 机组数据"})
@RestController
public class HeatNetworkDataController {

    @Autowired
    HeatNetworkDataService service;

    @GetMapping("/system/networkData/selectByTime")
    public R<List<HeatNetworkData>> selectByTime(@RequestParam Data start, @RequestParam Data end){
        return service.selectByTime(start,end);
    }

    @ApiOperation(value="查询全部机组数据")
    @GetMapping("/system/networkData/findAll")
    public R<IPage<HeatNetworkDataDTO>> findAll(@RequestParam(name = "current") Integer current,
                                                @RequestParam(name = "size") Integer size,
                                                @RequestParam(name = "name") String name){
        return service.findAll(new Page<HeatNetworkData>(current,size),name);
    }

    @ApiOperation(value="新增机组数据")
    @PostMapping("/system/networkData/add")
    public R<HeatNetworkData> add(@RequestBody HeatNetworkData data){
        return service.add(data);
    }


    @ApiImplicitParam(name = "company",value = "公司名称",dataType = "string",paramType = "query",required = true)
    @ApiOperation(value="查询机组的实时数据（一般为五分钟更新一次）")
    @GetMapping("/system/networkData/selectByRealTime")
    public R<HeatNetworkDataDTO> selectByRealTime(@RequestParam(name = "company") String company){
        return service.selectByRealTime(company);
    }


    @ApiImplicitParam(name = "company",value = "公司名称",dataType = "string",paramType = "query",required = true)
    @ApiOperation(value="查询机组的历史数据（查询当前机组的近五十条数据，用于折线图）")
    @GetMapping("/system/networkData/selectHeatDataHistory")
    public R<List<HeatNetworkDataDTO>> selectHeatDataHistory(@RequestParam(name = "stationId") Integer stationId){
        return service.selectHeatDataHistory(stationId);
    }


}
