package com.mzl.logbackdemo1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   LogTestController
 * @Description: 日志配置打印控制器
 * @Author: mzl
 * @CreateDate: 2020/11/11 18:10
 * @Version: 1.0
 */
@RestController
@Slf4j
public class LogTestController {

    /**
     * 日志测试
     * @param id
     * @return
     */
    @GetMapping("/test")
    public Integer test(Integer id){
        log.info("info的日志： 【{}】", id);
        log.warn("warn的日志： 【{}】", id);
        log.error("error的日志：【{}】", id);
        return id;
    }
}
