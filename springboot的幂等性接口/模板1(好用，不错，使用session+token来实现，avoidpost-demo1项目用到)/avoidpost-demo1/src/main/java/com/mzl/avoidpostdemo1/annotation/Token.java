package com.mzl.avoidpostdemo1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * token注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    /**
     * 是否创建新的token
     * @return
     */
    boolean generate() default false;

    /**
     * 是否移除token
     * @return
     */
    boolean remove() default false;

}
