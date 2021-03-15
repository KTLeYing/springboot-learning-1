package com.mzl.ratelimiterdemo1.annotation;

import com.mzl.ratelimiterdemo1.enums.LimitType;

import java.lang.annotation.*;

/**
 * 限流注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Limiter {

    /**
     * 资源的名字
     * @return
     */
    String name() default "";

    /**
     * 资源的key
     * @return
     */
    String key() default "";

    /**
     * 资源的前缀
     * @return
     */
    String prefix() default "";

    /**
     * 给定的时间段
     * @return
     */
    int period();

    /**
     * 最多的访问限定次数
     * @return
     */
    int count();

    /**
     * 资源类型
     * @return
     */
    LimitType limitType() default LimitType.CUSTOMER;

}
