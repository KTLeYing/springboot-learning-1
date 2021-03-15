package com.mzl.ticketgrabbing.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.ticketgrabbing.entity.Ticket;
import com.mzl.ticketgrabbing.service.ResultService;
import com.mzl.ticketgrabbing.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   MQReceiver
 * @Description: 消息队列接受者,从queue获取数据
 * @Author: mzl
 * @CreateDate: 2021/1/11 21:38
 * @Version: 1.0
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ResultService resultService;

    /**
     * 消费者接收, @RabbitListener(queues = MQConstant.QUEUE)定义监听的消息队列
     */
    @RabbitListener(queues = MQConstant.QUEUE)
    public void receive(String message){
        System.out.println("消费者接受队列中的消息" + message + "成功");
        //消费者接收到的是字符串实体类，要把字符串转换为实体类对象
        JSONObject jsonObject = JSON.parseObject(message);
        Message message1 = JSONObject.toJavaObject(jsonObject, Message.class);
        Integer ticketId = message1.getTicketId();
        Integer userId = message1.getUserId();

        //查询当前票的剩余数量
        Ticket ticket = ticketService.findCount(ticketId).get();
        if (ticket.getCount() > 0){   //如果当前票数大于0，则可以抢票成功
            System.out.println(ticket.getCount());
            //先判断该用户是否已经抢过该票
            Boolean exist = resultService.existByUserId(userId);
            if (!exist){    //没有抢过票，则可以抢
                //减少库存操作
                int num = ticketService.reduceCount(ticketId);
                //添加抢票记录
                resultService.addResult(ticketId, userId);
            }
        }

    }

}
