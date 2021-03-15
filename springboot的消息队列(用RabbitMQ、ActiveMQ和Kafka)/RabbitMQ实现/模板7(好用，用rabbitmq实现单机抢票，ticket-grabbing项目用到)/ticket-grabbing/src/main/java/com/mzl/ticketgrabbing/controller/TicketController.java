package com.mzl.ticketgrabbing.controller;

import com.mzl.ticketgrabbing.entity.Ticket;
import com.mzl.ticketgrabbing.mq.MQSender;
import com.mzl.ticketgrabbing.mq.Message;
import com.mzl.ticketgrabbing.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TicketController
 * @Description: 抢票控制器
 * @Author: mzl
 * @CreateDate: 2021/1/11 17:24
 * @Version: 1.0
 */
@RestController
public class TicketController  {

    @Autowired
    private MQSender mqSender;
    @Autowired
    private TicketService ticketService;

    /**
     * 抢票，减少库存
     * @param ticketId
     * @param userId
     * @return
     */
    @RequestMapping("/reduceCount")
    public String reduceCount(int ticketId, int userId){
        System.out.println(ticketId);
        System.out.println(userId);
        Message message = new Message();
        message.setTicketId(ticketId);
        message.setUserId(userId);
        mqSender.sendMessage(message);    //使用消息队列来存储抢票人的信息，然后给消费者获取来处理
        return "恭喜你抢票成功！";
    }

    /**
     * 查出某张票的信息
     * @param ticketId
     * @return
     */
    @RequestMapping("/findOneTicket")
    public Ticket findOneTicket(Integer ticketId){
        return  ticketService.findCount(ticketId).get();
    }

    /**
     * 票数减1
     * @return
     */
    @RequestMapping("/reduceCount1")
    public String reduceCount1(int ticketId){
        int num = ticketService.reduceCount(ticketId);
        if (num > 0){
            return "抢票成功！";
        }else {
            return "抢票失败！";
        }
    }



}
