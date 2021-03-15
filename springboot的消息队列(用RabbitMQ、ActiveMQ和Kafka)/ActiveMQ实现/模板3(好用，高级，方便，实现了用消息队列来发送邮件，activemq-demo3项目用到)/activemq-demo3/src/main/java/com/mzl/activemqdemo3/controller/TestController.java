package com.mzl.activemqdemo3.controller;

import com.mzl.activemqdemo3.pojo.Mail;
import com.mzl.activemqdemo3.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TestController
 * @Description: 测试控制器
 * @Author: mzl
 * @CreateDate: 2020/11/24 22:06
 * @Version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private Producer producer;

    /**
     * 生产者发送消息到队列（邮箱的相关内容）
     * @return
     */
    @RequestMapping("send")
    public String send(Mail mail) {
        return producer.send(mail);
    }


}
