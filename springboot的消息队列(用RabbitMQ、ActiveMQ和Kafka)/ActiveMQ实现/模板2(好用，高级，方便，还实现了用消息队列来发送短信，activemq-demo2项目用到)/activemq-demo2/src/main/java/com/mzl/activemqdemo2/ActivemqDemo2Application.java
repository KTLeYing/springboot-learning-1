package com.mzl.activemqdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActivemqDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(ActivemqDemo2Application.class, args);
    }

}
