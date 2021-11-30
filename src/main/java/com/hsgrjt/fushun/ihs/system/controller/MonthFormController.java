package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.dto.WeekPlanAddDTO;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.MonthService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
