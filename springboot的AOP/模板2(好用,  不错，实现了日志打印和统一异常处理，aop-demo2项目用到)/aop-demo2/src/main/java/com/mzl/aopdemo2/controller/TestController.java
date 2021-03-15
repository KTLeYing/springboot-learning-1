package com.mzl.aopdemo2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TestController
 * @Description: 统一异常处理和日志打印测试控制器
 * @Author: mzl
 * @CreateDate: 2021/3/11 9:49
 * @Version: 1.0
 */
@RestController
public class TestController {

    @RequestMapping("/doHello")
    public String hello(String name){
        return "Hello!" + name;
    }

    /**
     * 异常测试（算术异常）
     * 这里为@RequestMapping("/error")，/后面的请求路径名不能和方法名一致，因为是AOP的后置异常通知的影响,不能同时映射一个名字,不然springboot启动会报错
     */
    @RequestMapping("/doError")
    public Object error() {
        return 1 / 0;
    }

    /**
     * 异常测试（空指针异常）
     * @return
     */
    @RequestMapping("/doError1")
    public Object error1(){
        throw new NullPointerException("空指针异常");
    }

}
