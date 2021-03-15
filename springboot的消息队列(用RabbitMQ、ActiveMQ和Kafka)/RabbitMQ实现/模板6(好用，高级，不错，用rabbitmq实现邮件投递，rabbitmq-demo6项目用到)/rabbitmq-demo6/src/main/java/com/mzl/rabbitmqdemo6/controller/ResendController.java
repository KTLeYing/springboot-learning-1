package com.mzl.rabbitmqdemo6.controller;

import com.mzl.rabbitmqdemo6.common.Constant;
import com.mzl.rabbitmqdemo6.pojo.MsgLog;
import com.mzl.rabbitmqdemo6.service.MsgLogService;
import com.mzl.rabbitmqdemo6.utils.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   Controller
 * @Description: 重新投递发送失败的消息控制器
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:56
 * @Version: 1.0
 */
@RestController
@Slf4j
public class ResendController {

    @Autowired
    private MsgLogService msgLogService;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //最大的投递次数
    public static final int MAX_TRY_COUNT = 3;

  /**
   * 每30s从数据库拉取投递失败的消息, 重新去投递 验证定时任务的消息重投 实际应用场景中, 可能由于网络原因, 或者消息未被持久化MQ就宕机了,
   * 使得投递确认的回调方法ConfirmCallback没有被执行, 从而导致数据库该消息状态一直是投递中的状态, 此时就需要进行消息重投, 即使也许消息已经被消费了
   *定时任务只是保证消息100%投递成功, 而多次投递的消费幂等性需要消费端自己保证
   *我们可以将回调和消费成功后更新消息状态的代码注释掉, 开启定时任务, 查看是否重投
   * 可以看到, 消息会重投3次, 超过3次放弃, 将消息状态置为投递失败状态, 出现这种非正常情况, 就需要人工介入排查原因
   */
  @Scheduled(cron = "0/30 * * * * ?")
  public void resend() {
        log.info("开始执行定时任务(重新投递消息)");

        List<MsgLog> msgLogs = msgLogService.selectTimeoutMsg();
        msgLogs.forEach(msgLog -> {   //遍历投递失败的消息
            String msgId = msgLog.getMsgId();
            if (msgLog.getTryCount() >= MAX_TRY_COUNT){
                msgLogService.updateStatus(msgId, Constant.MsgLogStatus.DELIVER_FAIL);
                log.info("超过最大重试次数, 消息投递失败, msgId: {}", msgId);
            }else {
                // 更新投递数，投递次数+1
                msgLogService.updateTryCount(msgId, msgLog.getNextTryTime());
                CorrelationData correlationData = new CorrelationData(msgId);
                rabbitTemplate.convertAndSend(msgLog.getExchange(), msgLog.getRoutingKey(), MessageHelper.objToMsg(msgLog.getMsg()), correlationData);  //重新投递

                log.info("第 " + (msgLog.getTryCount() + 1) + " 次重新投递消息");
            }
        });

        log.info("定时任务执行结束(重新投递消息)");
    }

}
