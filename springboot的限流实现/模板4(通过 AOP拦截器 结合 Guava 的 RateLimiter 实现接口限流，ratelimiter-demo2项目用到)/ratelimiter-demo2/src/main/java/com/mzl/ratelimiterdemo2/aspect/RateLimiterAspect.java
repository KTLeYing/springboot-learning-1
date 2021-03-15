package com.mzl.ratelimiterdemo2.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.mzl.ratelimiterdemo2.annotation.MyRateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @ClassName :   RateLimiterAspect
 * @Description: 配置限流切面，相当于拦截器（@Around）
 * @Author: mzl
 * @CreateDate: 2020/11/9 19:58
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {

    //利用如果的gauva代替了平时redis(gauva既有redis的功能也有限流的功能)，方便
    private static final ConcurrentHashMap<String, RateLimiter> RATE_LIMITER_CACHE = new ConcurrentHashMap<>();

    /**
     * 配置切入点(相当于配置拦截的路径)
     */
    @Pointcut("execution(public * *(..)) && @annotation(com.mzl.ratelimiterdemo2.annotation.MyRateLimiter)")
    public void rateLimit(){

    }

    /**
     * 方法环绕，相当于拦截器，AOP拦截
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        // 通过 AnnotationUtils.findAnnotation 获取 controller控制器方法上自定义的RateLimiter 注解
        MyRateLimiter rateLimiter = AnnotationUtils.findAnnotation(method, MyRateLimiter.class);
        System.out.println("rateLimiter为： " + rateLimiter);
        if (rateLimiter != null && rateLimiter.qps() > MyRateLimiter.NOT_LIMITED){
            System.out.println("这里开始限流处理...");
            double qps = rateLimiter.qps();

            if (RATE_LIMITER_CACHE.get(method.getName()) == null){    //为空，创建该请求的redis，使用方法名作为key
                //初始化qps
                RATE_LIMITER_CACHE.put(method.getName(), RateLimiter.create(qps));  //创建该请求的redis，设置key-value
            }

            log.debug("【{}】的QPS设置为: {}", method.getName(), RATE_LIMITER_CACHE.get(method.getName()).getRate());
            System.out.println(method.getName() + " 的QPS设置为 " + RATE_LIMITER_CACHE.get(method.getName()).getRate());

            //尝试获取令牌,处理限流，用gauva自带的限流redis，方便，获取redis
            if (RATE_LIMITER_CACHE.get(method.getName()) != null && !RATE_LIMITER_CACHE.get(method.getName()).tryAcquire(rateLimiter.timeout(), rateLimiter.timeUtil())){
                throw new RuntimeException("请求太频繁，请稍后再试");
            }
        }
        return joinPoint.proceed();  //否则放行，可以正常访问，没限流
    }

}
