package com.mzl.logbackdemo1.aop;

import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @ClassName :   LogAop
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/11 17:43
 * @Version: 1.0
 */
@Component
@Aspect
@Slf4j
public class LogAop {

    /** 进入方法时间戳 */
    private long startTime;
    /** 方法结束时间戳*/
    private long endTime;

    public LogAop(){}

    /**
     * 指定切点是自定义的注解MyDesc，通过MyDesc注解间接把controller的方法和MyAspect连接起来，
     * controller方法给MyDesc赋值，MyDesc被指定为MyAspect的切入点
     */
    @Pointcut(value = "execution(* com.mzl.logbackdemo1.controller..*(..))")
    public void myLog(){
    }

    /**
     * 前置通知，接口方法执行前调用
     * @param joinPoint
     */
    @Before("myLog()")
    public void doBefore(JoinPoint joinPoint){
        // 获取当前的HttpServletRequest对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

        // 打印请求的内容
        startTime = System.currentTimeMillis();
        log.info("请求开始时间：{}", LocalDateTime.now());
        log.info("请求Url : {}", request.getRequestURL().toString());
        log.info("请求方式 : {}", request.getMethod());
        log.info("请求ip : {}", request.getRemoteAddr());
        log.info("请求内容类型 : {}", request.getContentType());
        log.info("请求参数 : {}", Arrays.toString(joinPoint.getArgs()));
        // 系统信息
        log.info("浏览器 : {}", userAgent.getBrowser().toString());
        log.info("浏览器版本 : {}", userAgent.getBrowserVersion());
        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
    }

    /**
     * 接口方法执行完成后调用
     * @param joinPoint
     */
    @After("myLog()")
    public void doAfter(JoinPoint joinPoint){
        //    System.out.println("doAfter");
    }

    /**
     * 返回通知 正常结束时进入此方法
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "myLog()")
    public void doAfterReturning(Object ret) {
        endTime = System.currentTimeMillis();
        log.info("请求结束时间 : {}", LocalDateTime.now());
        log.info("请求耗时 : {}", (endTime - startTime));
        // 处理完请求，返回内容
        log.info("请求返回 : {}", ret);
    }


    /**
     * 异常通知： 1. 在目标方法非正常结束，发生异常或者抛出异常时执行
     *
     * @param throwable
     */
    @AfterThrowing(throwing = "throwable", pointcut = "myLog()")
    public void doAfterThrowing(Throwable throwable){
        // 保存异常日志记录
        log.error("发生异常时间 : {}", LocalDateTime.now());
        log.error("抛出异常: {}", throwable.getMessage());
    }

}
