package com.mzl.quartzdemo1.config;

import com.mzl.quartzdemo1.job.QuartzJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   QuartzConfig
 * @Description: Quartz配置类（使用Scheduler 启动）
 * @Author: mzl
 * @CreateDate: 2020/12/18 19:08
 * @Version: 1.0
 */
@Configuration
@Slf4j
public class QuartzConfig {

    /***
     * Job 为作业的接口，为任务调度的对象；
     * JobDetail 用来描述 Job 的实现类及其他相关的静态信息；
     * Trigger 做为作业的定时管理工具，一个 Trigger 只能对应一个作业实例，而一个作业实例可对应多个触发器；
     * Scheduler 做为定时任务容器，是 Quartz 最上层的东西，它提携了所有触发器和作业，使它们协调工作，每个 Scheduler
     * 都存有 JobDetail 和 Trigger 的注册，一个 Scheduler 中可以注册多个 JobDetail 和多个 Trigger。
     * @return
     */

    /**
     * JobDetail 用来描述 Job 的实现类及其他相关的静态信息；
     * @return
     */
    @Bean
    public JobDetail jobDetail(){
        // 链式编程,可以携带多个参数,在Job类中声明属性 + setter方法
        return JobBuilder.newJob(QuartzJob.class).withIdentity("quartzJob")   //描述对应的job定时任务
                .usingJobData("name", "Hello World")   //usingJobData来设置对应的参数，自动调用job类的setter方法赋值
                .storeDurably().build();
    }

    /**
     * Trigger 做为作业的定时管理工具，一个 Trigger 只能对应一个作业实例，而一个作业实例可对应多个触发器；
     * @return
     */
    @Bean
    public Trigger jobTrigger(){
        // 每隔一分钟执行一次
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInHours(0)   //设置时
                .withIntervalInMinutes(1)  //设置分
//                .withIntervalInSeconds(5)   //设置秒
                .repeatForever();   //repeatForever是代表重复的意思
        return TriggerBuilder.newTrigger().forJob(jobDetail()).withIdentity("jobTrigger").withSchedule(simpleScheduleBuilder).build();
    }


}
