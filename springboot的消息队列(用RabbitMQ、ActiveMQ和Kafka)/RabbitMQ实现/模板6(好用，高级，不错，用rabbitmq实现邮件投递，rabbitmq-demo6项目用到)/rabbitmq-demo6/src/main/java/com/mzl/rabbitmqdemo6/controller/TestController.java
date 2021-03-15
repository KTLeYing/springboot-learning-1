package com.mzl.rabbitmqdemo6.controller;

import com.mzl.rabbitmqdemo6.common.ServerResponse;
import com.mzl.rabbitmqdemo6.pojo.Mail;
import com.mzl.rabbitmqdemo6.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TestController
 * @Description: 投递发送邮件测试控制器
 * @Author: mzl
 * @CreateDate: 2020/11/27 15:11
 * @Version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 利用rabbitmq投递发送邮件
     * @param mail
     * @param errors
     * @return
     */
    @RequestMapping("send")
    public ServerResponse sendMail(@Validated Mail mail, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ServerResponse.error(msg);
        }

        return testService.send(mail);
    }


}
