package com.mzl.apidesigndemo1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 防止重复提交
 */
@Target({ElementType.METHOD})   //使用在方法接口上的
@Retention(RetentionPolicy.RUNTIME)   //运行编译时就加载起作用
public @interface NotRepeatSubmit {

    //过期的时间，单位为毫秒
    long value() default 500000;

}
