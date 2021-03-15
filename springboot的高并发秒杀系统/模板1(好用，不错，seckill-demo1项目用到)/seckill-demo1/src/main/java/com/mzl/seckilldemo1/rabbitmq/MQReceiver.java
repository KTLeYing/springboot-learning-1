package com.mzl.seckilldemo1.rabbitmq;

import com.mzl.seckilldemo1.dao.SeckillDao;
import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.SeckillOrder;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.service.GoodsService;
import com.mzl.seckilldemo1.service.OrderService;
import com.mzl.seckilldemo1.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   MQReceiver
 * @Description: 消息队列接收者（消费着）
 * @Author: mzl
 * @CreateDate: 2021/3/6 9:11
 * @Version: 1.0
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SeckillService seckillService;

    /**
     * 消息队列的消费者（接受者）
     * @param message
     */
    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        System.out.println("消费者消费...");
        log.info("receive message:" + message);
        //消息队列存储秒杀的消息体SeckillMessage
        SeckillMessage seckillMessage = RedisService.stringToBean(message, SeckillMessage.class);
        User user = seckillMessage.getUser();
        long goodsId = seckillMessage.getGoodsId();

        GoodsDTO goodsDTO = goodsService.detail((int) goodsId);
        int stock = goodsDTO.getStockCount();
        //没有库存
        if (stock <= 0){
            return;
        }

        //判断重复秒杀
        SeckillOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null){
            return;
        }

        //减少库存， 下订单， 写入秒杀订单
        seckillService.seckill(user, goodsDTO);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info(" topic  queue1 message:" + message);
    }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:" + message);
    }

}
