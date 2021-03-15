package com.mzl.SSOdemo1.interceptor;

import java.lang.annotation.*;


/**
 * 要被进行拦截的单点登录接口的标志注解
 */
@Target(ElementType.METHOD)   //注解使用的目标是方法
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface InterceptFlag {

}
