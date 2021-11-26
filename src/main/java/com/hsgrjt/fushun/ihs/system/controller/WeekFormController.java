package com.hsgrjt.fushun.ihs.system.controller;

import com.hsgrjt.fushun.ihs.system.service.WeekFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: KenChen
 * @Description: 周报表controller
 * @Date: Create in  2021/11/27 上午12:31
 */
@RestController
public class WeekFormController {
    @Autowired
    WeekFormService weekFormService;

}
