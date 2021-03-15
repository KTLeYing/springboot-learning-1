package com.mzl.ratelimiterdemo2.controller;

import com.mzl.ratelimiterdemo2.annotation.MyRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   RateLimiterController
 * @Description: 限流控制控制器
 * @Author: mzl
 * @CreateDate: 2020/11/9 20:43
 * @Version: 1.0
 */
@RestController
@Slf4j
public class RateLimiterController {

    /**
     * 开启限流测试
     * @return
     */
    @MyRateLimiter(value = 1.0, timeout = 400)   //超时时间为100秒,它会自动被切面拦截类去监听拦截，相当于拦截器那样
    @GetMapping("/rateLimiter")
    public String rateLimiter(){
        log.info("rateLimiter被执行了...");
        System.out.println("rateLimiter被执行了...");
        return "你不能总是看到我，快速刷新我看一下！";
    }

    /**
     * 开启限流测试1
     * @return
     */
    @MyRateLimiter(value = 1.0, timeout = 1)   //超时时间为30秒
    @GetMapping("/rateLimiter1")
    public String rateLimiter1(){
        log.info("rateLimiter1被执行了...");
        System.out.println("rateLimiter1被执行了...");
        return "你不能总是看到我，快速刷新我看一下！";
    }

    /**
     * 不开启限流测试1
     * @return
     */
    @GetMapping("/noRateLimiter")
    public String noRateLimiter(){
        log.info("noRateLimiter被执行了...");
        System.out.println("noRateLimiter没用被执行了...");
        return "我永远不会被限流哦，一直刷新一直在.....";
    }

}
