package com.mzl.ratelimiterdemo1.interceptor;

import com.google.common.collect.ImmutableList;
import com.mzl.ratelimiterdemo1.annotation.Limiter;
import com.mzl.ratelimiterdemo1.enums.LimitType;
import com.mzl.ratelimiterdemo1.utils.BuildLuaUtils;
import com.mzl.ratelimiterdemo1.utils.IPAddressUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import sun.net.util.IPAddressUtil;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * @ClassName :   LimitInterceptor
 * @Description: 限流拦截器（AOP+redis）,核心
 * @Author: mzl
 * @CreateDate: 2020/11/9 1:35
 * @Version: 1.0
 */
@Aspect
@Configuration
//@Slf4j
public class LimitInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LimitInterceptor.class);

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    /**
     * 使用AOP拦截器
     * @param limitRedisTemplate
     */
    @Autowired
    public LimitInterceptor(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }

    /**
     * 在里面直接注入了切入点,代替@PointCut注解
     * @param joinPoint
     * @return
     */
    @Around("execution(public * *(..)) && @annotation(com.mzl.ratelimiterdemo1.annotation.Limiter)")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        //获取切入点的方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取方法的注解
        Limiter limiterAnnotation = method.getAnnotation(Limiter.class);

        LimitType limitType = limiterAnnotation.limitType();
        //获取资源名字
        String name = limiterAnnotation.name();
        String key;
        int limitPeriod = limiterAnnotation.period();
        //获取请求最多的参数
        int limitCount = limiterAnnotation.count();

        //获取redis的key
        switch (limitType){
            case IP:  //利用IP作为key
                key = IPAddressUtils.getIpAddress();
                break;
            case CUSTOMER:     //利用自己定义的顾客key
                key = limiterAnnotation.key();
                break;
            default:
                //不设置customer就直接使用大写的方法名作为key
                key = StringUtils.upperCase(method.getName());
        }

        //获取多个key,即用于判断每个请求的次数来决定是否限流
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limiterAnnotation.prefix(), key));
        try{
            String lusScript = BuildLuaUtils.buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(lusScript, Number.class);
            Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod); //key的value是请求的次数
            logger.info("Access try count is {} for name={} and key = {}", count, name, key);
            System.out.println("Access try count is" + count + "for name = " + name + " and key = " + key);
            if (count != null && count.intValue() <= limitCount){
                return joinPoint.proceed();  //返回目标进程，不拦截，放行该请求方法
            }else {
                throw new RuntimeException("您的访问次数过多，访问过于频繁，请稍后再访问！");   //抛出异常被捕获
            }
        } catch (Throwable e) {
            //获获处理异常
            if (e instanceof RuntimeException){
                throw new RuntimeException(e.getLocalizedMessage()); //抛出输出处理异常，像报错那样
            }
            throw new RuntimeException("服务器异常");
        }

    }
}
