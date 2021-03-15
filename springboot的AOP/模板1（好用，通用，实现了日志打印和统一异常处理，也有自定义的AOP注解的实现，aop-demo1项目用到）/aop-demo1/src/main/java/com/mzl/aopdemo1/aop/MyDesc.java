package com.mzl.aopdemo1.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * AOP的自定义注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})    //用注解创建和注册目标代理
@Retention(RetentionPolicy.RUNTIME)    //保留的注解，运行时的注解
public @interface MyDesc {
    //注解里提供了一个describe()的方法，供被切面的地方传参，如果不需要传参可以不写。
    String describe() default "我的自定义注解 MyDesc";
}
