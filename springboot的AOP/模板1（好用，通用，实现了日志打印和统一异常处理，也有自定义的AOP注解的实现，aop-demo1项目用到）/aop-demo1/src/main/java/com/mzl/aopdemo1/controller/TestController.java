package com.mzl.aopdemo1.controller;

import com.mzl.aopdemo1.aop.MyDesc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.Oneway;

/**
 * @ClassName :   TestController
 * @Description: aop测试控制器
 * @Author: mzl
 * @CreateDate: 2020/9/30 11:17
 * @Version: 1.0
 */
@RestController
public class TestController {

    /**
     * 一般的测试
     * @return
     */
    @RequestMapping("/test")
    public String test(){
        return "Aop Test Controller";
    }

    /**
     * 一般的测试（方法的参数列表接收参数，从http测试工具传参）
     * @param msg
     * @return
     */
    @RequestMapping("/test1")
    public String test1(String msg){
        return msg;
    }

  /**
   * 异常测试（算术异常）
   * 这里为@RequestMapping("/error")，/后面的请求路径名不能和方法名一致，因为是AOP的后置异常通知的影响,不能同时映射一个名字,不然springboot启动会报错
   */
  @RequestMapping("/doError")
  public Object error() {
        return 1/0;
    }

    /**
     * 异常测试（空指针异常）
     * @return
     */
    @RequestMapping("/doError1")
    public Object error1(){
        throw new NullPointerException();
    }

    /**
     * 自定义
     * @return
     */
    @RequestMapping("/myDescTest")
    @MyDesc(describe = "myDescTest")          //使用自定义注解
    public Object myDescTest(){
        return "这是我自定义的注解MyDesc......";
    }

}
