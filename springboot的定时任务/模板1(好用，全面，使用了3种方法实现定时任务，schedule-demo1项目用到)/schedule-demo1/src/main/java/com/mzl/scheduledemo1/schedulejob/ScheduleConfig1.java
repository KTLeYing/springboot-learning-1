package com.mzl.scheduledemo1.schedulejob;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName : ScheduleConfig
 * @Description: 自定义线程池，多个任务并发执行（异步）
 * @Author: mzl
 * @CreateDate: 2020/11/16
 * 1:49 @Version: 1.0
 */
@Component
@EnableScheduling
public class ScheduleConfig1{

    /** 对应ScheduleJob1定时任务类
     * 在SpringBoot项目中一般使用config配置类的方式添加配置@Configuration：表明该类是一个配置类@EnableAsync：开启异步事件的支持
     * 然后在定时任务的类或者方法上添加@Async 。最后重启项目，每一个任务都是在不同的线程中
     * @author 杨炜帆
     * @date : 2020/08/11
     */
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
