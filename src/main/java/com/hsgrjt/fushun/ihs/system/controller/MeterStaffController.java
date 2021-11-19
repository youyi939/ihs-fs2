package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/test/test")
    public String test(){
        return "hello";
    }


}
