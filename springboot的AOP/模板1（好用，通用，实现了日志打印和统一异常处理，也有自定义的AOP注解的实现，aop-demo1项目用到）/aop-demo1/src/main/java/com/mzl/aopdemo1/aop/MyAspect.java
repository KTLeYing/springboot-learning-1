package com.mzl.aopdemo1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   MyAspect
 * @Description: 自定义注解的切面类
 * @Author: mzl
 * @CreateDate: 2020/9/30 17:01
 * @Version: 1.0
 */
@Component
@Aspect
public class MyAspect {

    /**
     * 指定切点是自定义的注解MyDesc，通过MyDesc注解间接把controller的方法和MyAspect连接起来，
     * controller方法给MyDesc赋值，MyDesc被指定为MyAspect的切入点
     */
    @Pointcut(value = "@annotation(com.mzl.aopdemo1.aop.MyDesc)")
    public void myAspect1(){

    }

    /**
     * 前置通知，方法执行前通过
     * @param joinPoint
     * @throws Throwable
     */
    @Before("myAspect1()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        System.out.println("自定义注解测试接口前置开始...");
    }

    /**
     * 主要看一下@Around注解这里，如果需要获取在controller注解中赋给MyDesc的desc()里的值，就需要这种写法，这样MyDesc参数就有值了。
     * @param proceedingJoinPoint
     * @param myDesc
     * @return
     */
    @Around("@annotation(myDesc)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, MyDesc myDesc){
        // 获取注解里面的值
        System.out.println("自定义注解测试接口开始环绕：" + myDesc.describe());
        try{
            System.out.println("自定义注解方法环绕结果是: " + proceedingJoinPoint.proceed());
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }finally{
            System.out.println("自定义注解方法环绕结束...！！！");
        }
    }

}
