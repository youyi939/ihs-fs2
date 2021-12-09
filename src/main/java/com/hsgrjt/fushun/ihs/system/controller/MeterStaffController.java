package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.dto.DayFormDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterDataDTO;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterUpdateDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 水电热表controller
 * @Date: Create in  2021/11/19 下午2:42
 */
@Api(tags = {"CORE MeterStaff水电热记录表操作"})
@RestController
public class MeterStaffController {

    @Autowired
    MeterStaffService staffService;

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value = "新增水电热数据")
    @PostMapping("/system/meter/save")
    public R save(@RequestBody MeterStaffAddDTO dto, HttpServletRequest request) {
        User user = (User) request.getAttribute("ucm");
        staffService.save(dto);
        return R.ok("数据插入成功");
    }

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value = "查询用户所在公司下的机组的水电热数据原始数据表")
    @ApiImplicitParam(name = "type", value = "水/电/热", dataType = "String", paramType = "query", required = true)
    @GetMapping("/system/meter/findAll")
    public R<List<MeterDataDTO>> findAll(HttpServletRequest request, @RequestParam(name = "type") String type, @RequestParam(name = "month") String month) {
        User user = (User) request.getAttribute("ucm");
        String[] temp = month.split("-");
        return staffService.findAll(user, type, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
    }

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value = "查询日报表")
    @GetMapping("/system/meter/getDayFromWater")
    public R<List<DayFormDTO>> getDayFromWater(HttpServletRequest request, @RequestParam(name = "month") String month, @RequestParam(name = "type") String type) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        String[] temp = month.split("-");
        return staffService.getDayFromWater(user, Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), type);
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value = "更新水电热数据")
    @PostMapping("/system/meter/update")
    public R update(HttpServletRequest request, @RequestBody MeterUpdateDTO dto) {
        User user = (User) request.getAttribute("ucm");
        staffService.update(dto);
        return R.ok("更新成功");
    }


//    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value = "抄表")
    @GetMapping("/system/meter/updateByMachineId")
    public R updateByMachineId(HttpServletRequest request,
                               @RequestParam("machineId") Integer machineId,
                               @RequestParam("water") double water,
                               @RequestParam("power") double power,
                               @RequestParam("heat") double heat
                               ) {
        User user = (User) request.getAttribute("ucm");
        return staffService.updateByMachineId(machineId,water,power,heat);
    }


}
