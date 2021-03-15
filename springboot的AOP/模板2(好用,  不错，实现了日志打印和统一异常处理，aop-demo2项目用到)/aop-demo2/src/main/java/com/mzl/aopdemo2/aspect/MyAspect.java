package com.mzl.aopdemo2.aspect;

import com.mzl.aopdemo2.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @ClassName :   MyAspect
 * @Description: 自定义的切点类
 * @Author: mzl
 * @CreateDate: 2021/3/11 9:48
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class MyAspect {

    //定义当前本地线程
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.mzl.aopdemo2.controller.*.*(..))")
    public void myLog(){
    }

    /**
     * 前置通知
     */
    @Before("myLog()")
    public void doBefore(JoinPoint joinPoint){
        log.info("执行了前置通知...");
        Long nowTime = System.currentTimeMillis();
        log.info("开始时间为：" + nowTime);
        startTime.set(nowTime);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //记录内容请求
        log.info("URL(请求路径)：" + request.getRequestURI().toString());
        log.info("HTTP_METHOD（请求方法）:" + request.getMethod());
        log.info("IP:" + request.getRemoteAddr());
        log.info("CLASS_METHOD_1(类名.方法名): " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("CLASS_METHOD_2(类名.方法名): " + joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS(参数): " + Arrays.toString(joinPoint.getArgs()));
        log.info("------------------");
        log.info("------------------");
    }

    /**
     * 后置通知
     * @param
     */
    @After("myLog()")
    public void doAfter(JoinPoint joinPoint){
        log.info("执行了后置通知...");
        log.info("CLASS_METHOD(类名.方法名): " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("------------------");
        log.info("------------------");
    }


    /**
     * 通知环绕
     * 环绕通知 = 前置通知 + 目标方法（接口/切入点）执行，proceed()方法 + 目标方法返回结果 + 后置通知 + proceed()方法后面的 （proceed方法就是用于启动目标方法执行的）
     * 环绕通知 = 前置通知 + 目标方法执行(接口/切入点)，proceed()方法 + 异常通知 + 后置通知 + proceed()方法后面的 （proceed方法就是用于启动目标方法执行的）
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("myLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        log.info("执行了环绕通知，监测过程...");
        log.info("环绕通知的目标方法名："+ proceedingJoinPoint.getSignature().getName());
        Object object = null;
        try{
            object = proceedingJoinPoint.proceed();   //切入点启动(执行目标方法)，即执行接口
            log.info("方法环绕proceed，执行了切入点（接口）,结果（接口返回）为：" + object);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        log.info("------------------");
        log.info("------------------");
        return object;
    }

    /**
     * 在目标方法执行成功后，调用通知
     * 切入点返回结果之后执行,也就是都前置后置环绕都执行完了，这个就执行了
     * @param result
     */
    @AfterReturning(pointcut = "myLog()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result){
        log.info("执行了后置返回结果通知，...");
        Long endTime = System.currentTimeMillis();
        log.info("结束时间为：" + endTime);
        log.info("耗时（毫秒）：" + (endTime - startTime.get()));
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println(className + " 的 " + methodName + " 方法执行结果为：" + result);
        log.info("------------------");
        log.info("------------------");
    }

    /**
     * 后置异常通知，在接口抛出异常时执行
     * 这个是在切入执行报错的时候执行的
     */
    @AfterThrowing(value = "myLog()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) throws Exception {
        log.info("执行了后置异常通知，抛出异常执行...");
        log.info("抛出的异常为：" + e.getMessage());
        log.info("------------------");
        log.info("------------------");
    }


}
