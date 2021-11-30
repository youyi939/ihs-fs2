package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.SelectTime;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.MonthService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2021/11/30 下午3:31
 */
@RestController
public class MonthFormController {

    @Autowired
    MonthService monthService;

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="新增月报表记录")
    @PostMapping("/system/monthPlan/save")
    public R save(@RequestBody WeekPlanAddDTO dto, HttpServletRequest request) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        monthService.save(dto,user);
        return R.ok("数据插入成功");
    }

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="查询月报表时间（分组）")
    @GetMapping("/system/monthPlan/selectTime")
    public R selectTime( HttpServletRequest request) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        return monthService.selectTime();
    }


    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="查询月报表对象")
    @PostMapping("/system/monthPlan/selectMonthFormByTime")
    public R selectMonthFormByTime(HttpServletRequest request, @RequestBody SelectTime selectTime) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        return monthService.selectMonthFormByTime(selectTime,user);
    }

    @RequirePermission(Permissions.S_INIT)
    @ApiOperation(value="删除月报表对象")
    @PostMapping("/system/monthPlan/deleteMonthForm")
    public R deleteMonthForm(HttpServletRequest request, @RequestBody SelectTime selectTime) throws ParseException {
        User user = (User) request.getAttribute("ucm");
        monthService.deleteMonthForm(selectTime);
        return R.ok("删除成功");
    }

}
