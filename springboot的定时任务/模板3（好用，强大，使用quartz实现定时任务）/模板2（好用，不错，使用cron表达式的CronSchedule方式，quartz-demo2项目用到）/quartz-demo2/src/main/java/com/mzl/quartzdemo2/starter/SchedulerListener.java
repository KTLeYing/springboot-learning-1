package com.mzl.quartzdemo2.starter;

import com.mzl.quartzdemo2.schedule.QuartzCronScheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   SchedulerListener
 * @Description: 定时任务启动器（方式1：定时启动执行定时任务），一般不这样设计，开启程序后直接就立即自动执行定时任务
 * @Author: mzl
 * @CreateDate: 2020/12/19 2:59
 * @Version: 1.0
 */
@Configuration
@Component
@EnableScheduling   //开启支持定时任务注解，才能在里面使用定时任务
public class SchedulerListener {

    @Autowired
    private QuartzCronScheduler quartzCronScheduler;

    /**
     * 定时启动执行定时任务
     * @throws SchedulerException
     */
//    @Scheduled(cron = "0 47 16 24 1 ?")
//    public void schedule() throws SchedulerException {
//        System.out.println(">>>>>>>>>>>>>>>定时任务开始执行<<<<<<<<<<<<<");
//        quartzCronScheduler.scheduleJobs();   //执行启动定时任务
//    }



}
