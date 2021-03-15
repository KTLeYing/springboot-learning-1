package com.mzl.ratelimiterdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TestController
 * @Description: 测试控制器
 * @Author: mzl
 * @CreateDate: 2020/9/27 21:07
 * @Version: 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Sentinel";
    }

}
