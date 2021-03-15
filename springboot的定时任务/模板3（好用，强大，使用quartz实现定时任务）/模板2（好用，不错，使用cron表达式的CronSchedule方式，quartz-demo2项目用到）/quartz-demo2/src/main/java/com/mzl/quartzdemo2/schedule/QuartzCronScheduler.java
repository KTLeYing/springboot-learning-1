package com.mzl.quartzdemo2.schedule;

import com.mzl.quartzdemo2.job.QuartzJob1;
import com.mzl.quartzdemo2.job.QuartzJob2;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   QuartzScheduler
 * @Description: quartz配置定时任务
 * @Author: mzl
 * @CreateDate: 2020/12/19 2:36
 * @Version: 1.0
 */
@Component
public class QuartzCronScheduler {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    /**
     * 配置定时任务容器1---定时任务1
     * @param scheduler
     * @throws SchedulerException
     */
    public void scheduleJob1(Scheduler scheduler) throws SchedulerException {
        //JobDetail描述要执行的定时任务JOb的详细信息
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob1.class).withIdentity("job1", "group1").build();
        // 6的倍数秒执行 也就是 6 12 18 24 30 36 42 ....
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/6 * * * * ?");    //定时任务的cron表达式
        //定时任务触发器（定时管理器）
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").
                forJob(jobDetail).usingJobData("name", "MZL--【1】").withSchedule(scheduleBuilder).build();
        //把jobDetail和cronTrigger注册到定时任务容器中起作用，使trigger和jobDetail协同工作,最后完成某次定时任务的执行
        scheduler.scheduleJob(jobDetail, cronTrigger);

    }

    /**
     * 配置定时任务容器2------定时任务2
     * @param scheduler
     * @throws SchedulerException
     */
    public void scheduleJob2(Scheduler scheduler) throws SchedulerException {
        //JobDetail描述要执行的定时任务JOb的详细信息
        JobDetail jobDetail = JobBuilder.newJob(QuartzJob2.class).withIdentity("job2", "group2").build();
        // 6的倍数秒执行 也就是 6 12 18 24 30 36 42 ....
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/9 * * * * ?");    //定时任务的cron表达式
        //定时任务触发器（定时任务管理器）
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group2").
                forJob(jobDetail).usingJobData("name", "MZL--【2】").withSchedule(scheduleBuilder).build();
        //把jobDetail和cronTrigger注册到定时任务容器中起作用，使trigger和jobDetail协同工作,最后完成某次定时任务的执行
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 公用的定时容器启动器，同时启动多个定时任务(不一定先执行定时任务1，与顺序无关，线程来的，随机执行)
     * @throws SchedulerException
     */
    public void scheduleJobs() throws SchedulerException{
        Scheduler scheduler = schedulerFactoryBean.getScheduler();  //获取定时任务容器
        scheduleJob1(scheduler);
        scheduleJob2(scheduler);
    }

}
