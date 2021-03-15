package com.mzl.rabbitmqdemo1.controller;

import com.mzl.rabbitmqdemo1.sender.FirstSender;
import com.mzl.rabbitmqdemo1.sender.SecondSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author zhuzhe
 * @date 2018/5/25 16:00
 * @email 1529949535@qq.com
 */
@RestController
public class SendController {

    @Autowired
    private FirstSender firstSender;

    @Autowired
    private SecondSender secondSender;

    /***
     * 发送消息接口1（只能用get请求，不能用post，不然会报错）
     * @param message
     * @return
     */
    @GetMapping("/send1")
    public String send1(String message){
        String uuid = UUID.randomUUID().toString();
        firstSender.send(uuid,message);
        return uuid + ": " + message;
    }

    /***
     * 发送消息接口2（只能用get请求，不能用post，不然会报错）
     * @param message
     * @return
     */
    @GetMapping("/send2")
    public String send2(String message){
        String uuid = UUID.randomUUID().toString();
        secondSender.send(uuid, message);
        return uuid + ": " + message;
    }

}
