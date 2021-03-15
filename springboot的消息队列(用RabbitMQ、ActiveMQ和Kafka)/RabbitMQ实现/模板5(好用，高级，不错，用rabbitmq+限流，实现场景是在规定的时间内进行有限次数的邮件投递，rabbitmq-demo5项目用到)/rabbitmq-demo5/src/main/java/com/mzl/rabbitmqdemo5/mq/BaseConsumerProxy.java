package com.mzl.rabbitmqdemo5.mq;

import com.mzl.rabbitmqdemo5.common.Constant;
import com.mzl.rabbitmqdemo5.pojo.MsgLog;
import com.mzl.rabbitmqdemo5.service.MsgLogService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @ClassName :   BaseConsumerProxy
 * @Description: 基本的消费者代理
 * @Author: mzl
 * @CreateDate: 2020/10/23 12:20
 * @Version: 1.0
 */
@Slf4j
public class BaseConsumerProxy {

    private Object target;

    private MsgLogService msgLogService;

    public BaseConsumerProxy(Object target, MsgLogService msgLogService) {
        this.target = target;
        this.msgLogService = msgLogService;
    }

    public Object getProxy(){
        ClassLoader classLoader = target.getClass().getClassLoader();  //从代理中获取类加载器
        Class[] interfaces = classLoader.getClass().getInterfaces();  //从类加载器中获取接口

        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, ((proxy1, method, args) -> {
            Message message = (Message) args[0];  //0下标代表发送的消息
            Channel channel = (Channel) args[1];  //1下标代表消息发送到交换机的渠道

            String correlationId = getCorrelationId(message);
            if (isConsumed(correlationId)){  // 消费幂等性, 防止消息被重复消费
                log.info("重复消费, correlationId: {}", correlationId);
                return null;
            }

            MessageProperties messageProperties = message.getMessageProperties();
            long tag = messageProperties.getDeliveryTag();

            try{
                Object result = method.invoke(target, args);   //真正消费的业务逻辑
                msgLogService.updateStatus(correlationId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
                channel.basicAck(tag, false); //消费确认，确认后消息不在队列中了，消失了，通过渠道设置，因为渠道是监听返回确定的,会把确认号返回给生产者
                return result;
            } catch (Exception e) {
                log.error("getProxy error", e);
                channel.basicNack(tag, false, true);  //消费失败，并告诉消费者发送失败
                e.printStackTrace();
                return null;
            }
        }));

        return proxy;  //返回消费者消费消息的动态代理者
    }

    /**
     * 消息是否已被消费
     *
     * @param correlationId
     * @return
     */
    private boolean isConsumed(String correlationId) {
        MsgLog msgLog = msgLogService.selectByMsgId(correlationId);
        if (msgLog == null || msgLog.getStatus().equals(Constant.MsgLogStatus.DELIVER_FAIL)){
            return true;
        }
        return false;
    }

    /**
     * 获取CorrelationId
     *
     * @param message
     * @return
     */
    private String getCorrelationId(Message message) {
        String correlationId = null;

        MessageProperties messageProperties = message.getMessageProperties();
        Map<String, Object> headers = messageProperties.getHeaders();
        for (Map.Entry entry : headers.entrySet()){
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (key.equals("spring_returned_message_correlation")){
                correlationId = (String) value;
            }
        }
        return correlationId;
    }

}
