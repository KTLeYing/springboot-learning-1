package com.mzl.activemqdemo2.controller;

import com.mzl.activemqdemo2.consumer.Consumer;
import com.mzl.activemqdemo2.producer.Producer;
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
     * 生产者发送消息到队列（手机号码）
     * @return
     */
    @RequestMapping("send")
    public String send(String phone){
        return producer.send(phone);
    }


}
