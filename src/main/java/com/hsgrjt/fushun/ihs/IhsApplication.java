package com.hsgrjt.fushun.ihs;

import com.hsgrjt.fushun.ihs.timer.TimedTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class IhsApplication {

    @Bean
    public TimedTask getTask() {
        return new TimedTask();
    }

    public static void main(String[] args) {
        SpringApplication.run(IhsApplication.class, args);
    }

}
