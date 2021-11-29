package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.WeekForm;
import com.hsgrjt.fushun.ihs.system.entity.dto.MeterStaffAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.WeekFormService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 周报表controller
 * @Date: Create in  2021/11/27 上午12:31
 */
@RestController
public class WeekFormController {
    @Autowired
    WeekFormService weekFormService;


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="新增周报表记录")
    @PostMapping("/system/weekPlan/save")
    public R save(@RequestBody WeekPlanAddDTO dto, HttpServletRequest request, @RequestParam("type")String type) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        weekFormService.save(dto,user,type);
        return R.ok("数据插入成功");
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="根据周数和年份查询周报表记录")
    @GetMapping("/system/weekPlan/selectByWeekNum")
    public R<List<WeekForm>> selectByWeekNum(HttpServletRequest request, @RequestParam("weekNum")Integer weekNum, @RequestParam("year")String year){
        User user = (User) request.getAttribute("ucm");
        return weekFormService.selectByWeekNum(weekNum,year);
    }

}
