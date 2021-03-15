package com.mzl.rabbitmqdemo5.config;

import com.mzl.rabbitmqdemo5.common.Constant;
import com.mzl.rabbitmqdemo5.service.MsgLogService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;


/**
 * @ClassName :   RabbitConfig
 * @Description: rabbitmq配置类
 * @Author: mzl
 * @CreateDate: 2020/10/23 10:51
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    private CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    private MsgLogService msgLogService;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        // 消息是否成功发送到Exchange
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {   //如果exchange返回一个消息ack确认号给生产者
                log.info("消息成功发送到Exchange");
                String msgId = correlationData.getId();
                //更新消息日志状态
                msgLogService.updateStatus(msgId, Constant.MsgLogStatus.DELIVER_SUCCESS);
            } else {
                log.info("消息发送到Exchange失败, {}, cause: {}", correlationData, cause);
            }
        });

        // 触发setReturnCallback回调必须设置mandatory=true, 否则Exchange没有找到Queue就会丢弃掉消息, 而不会触发回调
        rabbitTemplate.setMandatory(true);
        // 消息是否从Exchange路由到Queue, 注意: 这是一个失败回调, 只有消息从Exchange路由到Queue失败才会回调这个方法
        rabbitTemplate.setReturnCallback(((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息从Exchange路由到Queue失败: exchange: {}, route: {}, replyCode: {}, replyText: {}, message: {}",
                    exchange, routingKey, replyCode, replyText, message);
        }));

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    // 登录日志
    public static final String LOGIN_LOG_QUEUE_NAME = "login.log.queue";
    public static final String LOGIN_LOG_EXCHANGE_NAME = "login.log.exchange";
    public static final String LOGIN_LOG_ROUTING_KEY_NAME = "login.log.routing.key";

    /**
     * 创建并配置登录日志队列
     * @return
     */
    @Bean
    public Queue logUserQueue(){
        return new Queue(LOGIN_LOG_QUEUE_NAME, true);
    }

    /**
     * 创建并配置登录日志交换机
     * @return
     */
    @Bean
    public DirectExchange logUserExchange(){
        return new DirectExchange(LOGIN_LOG_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定登录日志队列到登录日志交换机上
     * @return
     */
    @Bean
    public Binding logUserBinding(){
        return BindingBuilder.bind(logUserQueue()).to(logUserExchange()).with(LOGIN_LOG_ROUTING_KEY_NAME);
    }



    // 发送邮件
    public static final String MAIL_QUEUE_NAME = "mail.queue";
    public static final String MAIL_EXCHANGE_NAME = "mail.exchange";
    public static final String MAIL_ROUTING_KEY_NAME = "mail.routing.key";

    /**
     * 创建并配置邮件队列
     * @return
     */
    @Bean
    public Queue mainQueue(){
        return new Queue(MAIL_QUEUE_NAME, true);
    }

    /**
     * 创建并配置邮件交换机
     * @return
     */
    @Bean
    public DirectExchange mailExchange(){
        return new DirectExchange(MAIL_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定邮件队列到邮件交换机上
     * @return
     */
    @Bean
    public Binding mailBinding(){
        return BindingBuilder.bind(mainQueue()).to(mailExchange()).with(MAIL_ROUTING_KEY_NAME);
    }


}
