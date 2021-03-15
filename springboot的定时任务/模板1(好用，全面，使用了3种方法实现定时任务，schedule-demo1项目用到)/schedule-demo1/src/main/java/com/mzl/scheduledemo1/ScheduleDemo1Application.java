package com.mzl.scheduledemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//开启Scheduling注解支持
@EnableScheduling
@SpringBootApplication
public class ScheduleDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleDemo1Application.class, args);
    }

}
