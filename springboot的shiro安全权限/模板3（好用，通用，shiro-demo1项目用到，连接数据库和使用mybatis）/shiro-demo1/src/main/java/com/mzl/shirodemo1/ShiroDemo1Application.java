package com.mzl.shirodemo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.mzl")
@SpringBootApplication
public class ShiroDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(ShiroDemo1Application.class, args);
    }

}
