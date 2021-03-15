package com.mzl.rabbitmqdemo5.controller;

import com.mzl.rabbitmqdemo5.annotation.AccessLimit;
import com.mzl.rabbitmqdemo5.annotation.ApiIdempotent;
import com.mzl.rabbitmqdemo5.common.ServerResponse;
import com.mzl.rabbitmqdemo5.pojo.Mail;
import com.mzl.rabbitmqdemo5.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @ClassName :   TestController
 * @Description: 测试控制器
 * @Author: mzl
 * @CreateDate: 2020/10/23 12:13
 * @Version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 幂等性测试接口
     * @return
     */
    @ApiIdempotent  //使用自定义注解，和框架注解用法一样，直接使用
    @RequestMapping("/testIdempotence")
    public ServerResponse testIdempotence(){
        return testService.testIdempotence();
    }

    /**
     * 限流测试接口(对哪个接口限流，直接在接口方法上添加@AccessLimit(maxCount = 3, seconds = 10)注解即可对某个接口实现限流，
     * 通用的，用自定义接口代替了拦截器的WbeMVCConfig拦截路径的配置，也可以使用配置拦截路径来代替自定义注解@AccessLimit来拦截处理请求)
     * @return
     */
    @RequestMapping("/accessLimit")
    @AccessLimit(maxCount = 3, seconds = 10)
    public ServerResponse accessLimit(){
        return testService.accessLimit();
    }

    /**
     * 发送邮件
     * @param mail
     * @return
     */
    @AccessLimit(maxCount = 3, seconds = 10)
    @RequestMapping("/send")
    public ServerResponse send(@Validated Mail mail, Errors errors){
        System.out.println(mail);
        if (errors.hasErrors()){
            String msg = errors.getFieldError().getDefaultMessage();
            return ServerResponse.error(msg);
        }
        return testService.send(mail);
    }


}
