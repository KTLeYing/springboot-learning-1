package com.example.springdemo3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   HelloController
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/22 17:56
 * @Version: 1.0
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/test")
    @ResponseBody
    public String hello(){
        return "Hello World";
    }
}
