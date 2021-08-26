package com.hsgrjt.fushun.ihs.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hsgrjt.fushun.ihs.annotations.RequirePermission;
import com.hsgrjt.fushun.ihs.system.entity.HeatMachine;
import com.hsgrjt.fushun.ihs.system.service.HeatMachineService;
import com.hsgrjt.fushun.ihs.utils.JsonMessage;
import com.hsgrjt.fushun.ihs.utils.Permissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 *  机组表CRUD
 * </p>
 *
 * @author 晟翼科技
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/system/machine")
public class HeatMachineController {

    @Autowired
    private HeatMachineService heatMachineService;

    @RequirePermission(Permissions.MACHINE_MANAGEMENT)
    @PostMapping("add")
    public String addMachine(@RequestBody HeatMachine machine) {
        if (heatMachineService.save(machine)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @RequirePermission(Permissions.MACHINE_MANAGEMENT)
    @GetMapping("delete/{id}")
    public String deleteMachine(@PathVariable String id) {
        if (heatMachineService.removeById(id)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @RequirePermission(Permissions.MACHINE_MANAGEMENT)
    @PostMapping("modify")
    public String modifyMachine(@RequestBody HeatMachine machine) {
        if (heatMachineService.updateById(machine)) {
            return JsonMessage.SUCCESS;
        } else {
            return JsonMessage.DATABASE_ERROR;
        }
    }

    @GetMapping("all")
    public List<HeatMachine> getAllHeatMachine() {
        return heatMachineService.list();
    }

    @GetMapping("company/{company}")
    public List<HeatMachine> getAllHeatMachineInCompany(@PathVariable String company) {
        QueryWrapper<HeatMachine> wrapper = new QueryWrapper<>();
        wrapper.eq("company", company)
                .orderByAsc("sort_id");
        return heatMachineService.list(wrapper);
    }

    @GetMapping("service_dep/{service_dep}")
    public List<HeatMachine> getAllHeatMachineInServiceDep(@PathVariable String service_dep) {
        QueryWrapper<HeatMachine> wrapper = new QueryWrapper<>();
        wrapper.eq("service_dep", service_dep)
                .orderByAsc("sort_id");
        return heatMachineService.list(wrapper);
    }

    @GetMapping("center_station/{center_station}")
    public List<HeatMachine> getAllHeatMachineInCenterStation(@PathVariable String center_station) {
        QueryWrapper<HeatMachine> wrapper = new QueryWrapper<>();
        wrapper.eq("center_station", center_station)
                .orderByAsc("sort_id");
        return heatMachineService.list(wrapper);
    }

    @GetMapping("station/{station}")
    public List<HeatMachine> getAllHeatMachineInStation(@PathVariable String station) {
        QueryWrapper<HeatMachine> wrapper = new QueryWrapper<>();
        wrapper.eq("station", station)
                .orderByAsc("sort_id");
        return heatMachineService.list(wrapper);
    }

    @GetMapping("{id}")
    public HeatMachine getHeatMachineById(@PathVariable String id) {
        return heatMachineService.getById(id);
    }

}

