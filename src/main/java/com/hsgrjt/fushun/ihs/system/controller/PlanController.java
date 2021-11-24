package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.Plan;
import com.hsgrjt.fushun.ihs.system.entity.User;
import com.hsgrjt.fushun.ihs.system.entity.vo.R;
import com.hsgrjt.fushun.ihs.system.service.PlanService;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: KenChen
 * @Description: 年计划controller
 * @Date: Create in  2021/11/24 下午2:02
 */
@Api(tags = {"CORE 计划指标记录表操作"})
@RestController
public class PlanController {

    @Autowired
    PlanService service;


    @ApiOperation(value="查询当前公司下的机组的计划数据")
    @RequirePermission(Permissions.S_INIT)
    @GetMapping("/system/plan/findAll")
    public R<List<Plan>> findAll(HttpServletRequest request){
        User user = (User) request.getAttribute("ucm");
        return service.findAll(user.getAllowCompanys());
    }

    @ApiOperation(value="更新计划数据")
    @RequirePermission(Permissions.S_INIT)
    @PostMapping("/system/plan/update")
    public R update(HttpServletRequest request, @RequestBody Plan plan){
        User user = (User) request.getAttribute("ucm");
        service.updateById(plan);
        return R.ok("更新成功");
    }


}
