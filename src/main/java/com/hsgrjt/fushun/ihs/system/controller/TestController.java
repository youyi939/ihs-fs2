package com.hsgrjt.fushun.ihs.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KenChen
 * @Description:
 * @Date: Create in  2022/3/4 12:30 下午
 */
@RestController
public class TestController {

    @GetMapping(value = "/test")
    public String test(){
        return "你好";
    }

}
