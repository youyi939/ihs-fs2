package com.hsgrjt.fushun.ihs.system.controller;


import com.hsgrjt.fushun.ihs.system.entity.HeatCompany;
import com.hsgrjt.fushun.ihs.system.service.HeatCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *那三个公司的controller
 * @author 晟翼科技
 * @since 2021-08-11
 */
@RestController
@RequestMapping("/system/company")
public class HeatCompanyController {

    @Autowired
    private HeatCompanyService heatCompanyService;

    @GetMapping("all")
    public List<HeatCompany> getAllCompany() {
        return heatCompanyService.list();
    }

}