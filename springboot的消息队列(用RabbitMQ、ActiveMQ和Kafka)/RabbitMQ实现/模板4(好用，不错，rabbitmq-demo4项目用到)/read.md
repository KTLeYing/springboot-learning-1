如何保证rabbitMQ消息不丢失
主要是针对特别重要的消息，必须成功投递、成功消费的情况。案例如下：

springboot + rabbitmq 保证消息100%投递成功并被消费，请参考

原作者 github地址： https://github.com/wangzaiplus/springboot/tree/wxw

本人删减后的github地址：https://github.com/wusong1994/springBoot-rabbitmq

生产者成功投递确认：如果投递失败超过重试次数则不管了，避免非正常情况下无限循环重试，消耗服务器资源。投递失败重试可以专门用一个定时任务进行处理。

消费者成功消费确认：具体消费者业务异常，需要重新消费的情况下，将消息放回到队列中即可（不要确认成功），消息下次会被重新投递到消费者进行消费。