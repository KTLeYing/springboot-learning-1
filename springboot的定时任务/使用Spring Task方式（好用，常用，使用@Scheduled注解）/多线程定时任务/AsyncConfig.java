package com.jobgo.task;


import com.jobgo.clockin.pojo.ClockIn;
import com.jobgo.clockin.service.ClockInService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;


/**
 * 在SpringBoot项目中一般使用config配置类的方式添加配置@Configuration：表明该类是一个配置类@EnableAsync：开启异步事件的支持
 * 然后在定时任务的类或者方法上添加@Async 。最后重启项目，每一个任务都是在不同的线程中
 * @author 杨炜帆
 * @date : 2020/08/11
 */
@Configuration
@EnableAsync
public class AsyncConfig {
     /*
    此处成员变量应该使用@Value从配置中读取
     */
    private int corePoolSize = 10;
    private int maxPoolSize = 200;
    private int queueCapacity = 10;

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.initialize();
        return executor;
    }
}
