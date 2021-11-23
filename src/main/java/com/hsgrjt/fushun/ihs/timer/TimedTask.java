package com.hsgrjt.fushun.ihs.timer;


import com.hsgrjt.fushun.ihs.system.service.MeterStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */

@Component
public class TimedTask {

    @Autowired
    MeterStaffService meterStaffService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetIntegral() {
        meterStaffService.initDataEveryDay();
    }
}
