package com.mzl.scheduledemo1.schedulejob;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @ClassName : ScheduleConfig
 * @Description: 默认线程池，单线程，多个任务串行执行（同步）
 * @Author: mzl
 * @CreateDate: 2020/11/16
 * 1:49 @Version: 1.0
 */
@Component
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    /**对应ScheduleJob定时任务类
     * Spring的Schecule默认是单线程执行的，如果你定义了多个任务，那么他们将会被串行执行，会严重不满足你的预期,这样对于我们的多任务调度可能会是致命的，
     * 当多个任务并发（或需要在同一时间）执行时，任务调度器就会出现时间漂移，任务执行时间将不确定。
     * 所以为了解决该问题，需要重写configureTasks方法,taskRegistrar设置池自定义线程池，具体如下：
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(customScheduler());
    }

    @Bean(destroyMethod = "shutdown")
    public ExecutorService customScheduler(){
        return Executors.newScheduledThreadPool(20);
    }


}
