package com.mzl.logbackdemo2.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   LogbackController
 * @Description: 日志控制器
 * @Author: mzl
 * @CreateDate: 2020/11/11 18:22
 * @Version: 1.0
 */
@RestController
@Slf4j
public class LogbackController {

//    private Logger logger = LoggerFactory.getLogger(getClass());   //可以直接用@Slf4j注解代替，两者都是一样的，同一个东西来的

    @GetMapping("/test")
    public String test(){
        log.trace("日志输出 trace");
        log.debug("日志输出 debug");
        log.info("日志输出 info");
        log.warn("日志输出 warn");
        log.error("日志输出 error");

        return "Logback Demo...";
    }

}
