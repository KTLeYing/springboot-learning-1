package com.mzl.rabbitmqdemo2.controller;

import com.mzl.rabbitmqdemo2.config.RabbitMqConfig;
import com.mzl.rabbitmqdemo2.entity.Book;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   BookController
 * @Description: 书本控制器
 * @Author: mzl
 * @CreateDate: 2020/10/20 20:18
 * @Version: 1.0
 */
@RestController
public class BookController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     *  this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book); 对应 {@link BookHandler#listenerAutoAck}
     *  this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book); 对应 {@link BookHandler#listenerManualAck}
     *
     */
    @GetMapping("/defaultMessage")
    public void defaultMessage(){
        Book book = new Book();
        book.setId(1);
        book.setName("数据结构");
        rabbitTemplate.convertAndSend(RabbitMqConfig.DEFAULT_BOOK_QUEUE, book);
        rabbitTemplate.convertAndSend(RabbitMqConfig.MANUAL_BOOK_QUEUE, book);
    }

}
