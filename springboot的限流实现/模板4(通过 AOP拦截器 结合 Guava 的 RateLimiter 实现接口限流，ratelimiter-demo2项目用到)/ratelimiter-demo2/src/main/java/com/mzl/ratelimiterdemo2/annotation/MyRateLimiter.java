package com.mzl.ratelimiterdemo2.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 自定义的限流注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)   //运行时的保留
@Documented   //用于记录监听注解使用的信息
public @interface MyRateLimiter {

    int NOT_LIMITED = 0;

  //顾名思义 @AliasFor 表示别名，它可以注解到自定义注解的两个属性上，表示这两个互为别名，也就是说这两个属性其实同一个含，它表示与注解标注的属性互为别名的另一个属性的名字。
  //同时若自定义注解继承了另一个注解，要想让调用方能够设置继承过来的属性值，就必须在自定义注解中重新定义一个属性，同时声明该属性是父注解某个属性的别名。

  /**
   * qps，每秒访问的速率
   *
   * @return
   */
  @AliasFor("qps") // 这个注解不能省，用于获取每秒访问的速率,与qps()保持一样的值
  double value() default NOT_LIMITED;

    /**
     * qps，每秒访问的速率
     * @return
     */
    @AliasFor("value") //这个注解不能省，用于获取每秒访问的速率，与value()保持一样的值
    double qps() default NOT_LIMITED;

    /**
     * 超时时间
     * @return
     */
    int timeout() default 0;

    /**
     * 超时时间单位
     * @return
     */
    TimeUnit timeUtil() default TimeUnit.MILLISECONDS;

}
