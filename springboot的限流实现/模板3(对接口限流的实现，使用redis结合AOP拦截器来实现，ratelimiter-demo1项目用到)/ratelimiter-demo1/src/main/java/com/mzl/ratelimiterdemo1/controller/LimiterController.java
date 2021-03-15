package com.mzl.ratelimiterdemo1.controller;

import com.mzl.ratelimiterdemo1.annotation.Limiter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName :   LimiterController
 * @Description: 限流控制器
 * @Author: mzl
 * @CreateDate: 2020/11/9 1:37
 * @Version: 1.0
 */
@RestController
public class LimiterController {

    //自动的数字类
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 限流测试接口1, Redis中生成过期时间为 100s 的 key,此key最大的访问次数为10，即直到key失效后 或者 key存在且访问次数小于10才能正常访问
     * @return
     */
    @Limiter(key = "testLimiter1", period = 100, count = 10, name = "resource1", prefix = "limit-") //使用自定义注解，@Aspect那里会处理自定义的注解
    @GetMapping("testLimiter1")
    public int testLimiter1(){
        return ATOMIC_INTEGER.getAndIncrement() + 1;  //自增，计算此方法请求的次数,代替自己写的自增for循环，默认从0开始，只有程序重启从恢复从0开始
    }

    /**
     * 限流测试接口2，Redis中生成过期时间为 100s 的 key,此key最大的访问次数为10，即直到key失效后 或者 key存在且访问次数小于10才能正常访问
     * @return
     */
    @Limiter(key = "testLimiter2", period = 100, count = 5, name = "resource2", prefix = "limit-") //使用自定义注解，@Aspect那里会处理自定义的注解
    @GetMapping("testLimiter2")
    public int testLimiter2(){
        return ATOMIC_INTEGER.getAndIncrement() + 1;  //自增，计算此方法请求的次数,代替自己写的自增for循环，默认从0开始
    }


}
