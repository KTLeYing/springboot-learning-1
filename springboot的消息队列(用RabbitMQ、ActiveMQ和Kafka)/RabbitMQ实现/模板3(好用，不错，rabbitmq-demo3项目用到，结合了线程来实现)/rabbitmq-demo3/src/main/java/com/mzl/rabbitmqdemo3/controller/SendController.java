package com.mzl.rabbitmqdemo3.controller;

import com.mzl.rabbitmqdemo3.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName :   SendController
 * @Description:测试控制器
 * @Author: mzl
 * @CreateDate: 2020/10/20 22:07
 * @Version: 1.0
 */
@RestController
public class SendController {

    @Autowired
    private Sender sender;

    @GetMapping("/sendTest")
    public void sendTest() throws Exception{
        int num = 0;
        while (true){
            num++;
            String msg = new Date().toString();
            System.out.println("----------------第" + num +  "轮-------------------");
            sender.send(msg);
            Thread.sleep(10000);   //10000毫秒，也就是10秒发送一次
        }
    }

}
