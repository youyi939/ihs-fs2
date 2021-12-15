package com.hsgrjt.fushun.ihs.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.HeatNetworkData;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.HeatNetworkDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.HeatNetworkDataService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.text.ParseException;
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
    public R<IPage<HeatNetworkDataDTO>> findAll(@RequestParam(name = "current") Integer current, @RequestParam(name = "size") Integer size, @RequestParam(name = "name") String name){
        return service.findAll(new Page<HeatNetworkData>(current,size),name);
    }

    @ApiOperation(value="新增机组数据")
    @PostMapping("/system/networkData/add")
    public R<HeatNetworkData> add(@RequestBody HeatNetworkData data){
        return service.add(data);
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="查询机组的实时数据（一般为五分钟更新一次，用于实时数据查看）")
    @GetMapping("/system/networkData/selectByRealTime")
    public R<HeatNetworkDataDTO> selectByRealTime( HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return service.selectByRealTime(user.getAllowCompanys());
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiImplicitParam(name = "stationId",value = "机组id",dataType = "int",paramType = "query",required = true)
    @ApiOperation(value="查询机组的历史数据（查询当前机组的近五十条数据，用于折线图）")
    @GetMapping("/system/networkData/selectHeatDataHistory")
    public R<List<HeatNetworkDataDTO>> selectHeatDataHistory(@RequestParam(name = "stationId") Integer stationId){
        return service.selectHeatDataHistory(stationId);
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiImplicitParam(name = "machineId",value = "机组id",dataType = "int",paramType = "query",required = true)
    @ApiOperation(value="折线图查询数据接口")
    @GetMapping("/system/networkData/selectHeatDataHistoryForLine")
    public R<List<HeatNetworkDataDTO>> selectHeatDataHistoryForLine(
            @RequestParam(name = "machineId") Integer machineId,
            @RequestParam(name = "startTime") String startTime,
            @RequestParam(name = "stopTime") String stopTime
    ) throws ParseException {
        return service.selectHeatDataHistoryForLine(machineId,startTime,stopTime);
    }

}
