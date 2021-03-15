package com.mzl.rabbitmqdemo2.receicer;

import com.mzl.rabbitmqdemo2.config.RabbitMqConfig;
import com.mzl.rabbitmqdemo2.entity.Book;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   BookHandler
 * @Description: 书本消费者(接收者)
 * @Author: mzl
 * @CreateDate: 2020/10/20 19:57
 * @Version: 1.0
 */
@Component
public class BookReceiver {

    //相当于@Slf4j，对BookReceive类进行日志处理
    public static final Logger log = LoggerFactory.getLogger(BookReceiver.class);

    /**
     * 该方案是 spring-boot-data-amqp 默认的方式,不太推荐。具体推荐使用  listenerManualAck()</p>
     * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
     * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
     * 解决方案：手动ACK,或者try-catch 然后在 catch 里面讲错误的消息转移到其它的系列中去
     * spring.rabbitmq.listener.simple.acknowledge-mode=manual
     *
     * @param book 监听的内容（交换机在这里由消费者创建，也可以有生产者创建，在rabbitMq配置中绑定交换机在队列中即可）
     */
    @RabbitListener(queues = {RabbitMqConfig.DEFAULT_BOOK_QUEUE})
    public void listenerAutoAck(Book book, Message message, Channel channel){
        //  如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果未配置 acknowledge-mode 默认是会在消费完毕后自动ACK
        final long deliverTag = message.getMessageProperties().getDeliveryTag();
        try{
            log.info("[listenerAutoAck 监听的消息] - [{}]", book.toString());
            // 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliverTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            try{
                //处理失败,重新压入MQ
                channel.basicRecover();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 消费者自动监听
     * @param book
     * @param message
     * @param channel
     */
    @RabbitListener(queues = {RabbitMqConfig.MANUAL_BOOK_QUEUE})
    public void listenerManualAck(Book book, Message message, Channel channel){
        log.info("[listenerManualAck 监听的消息] - [{}]", book.toString());
        final long deliverTag = message.getMessageProperties().getDeliveryTag();
        try{
            //通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliverTag, false);
        } catch (Exception e) {
            e.printStackTrace();
            // 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }



}
