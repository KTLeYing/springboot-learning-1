package com.mzl.quartzdemo2.starter;

import com.mzl.quartzdemo2.schedule.QuartzCronScheduler;
import org.quartz.core.QuartzScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   StartListener
 * @Description: 定时任务启动器（方式2：程序启动时就启动执行定时任务），好用，开启程序后直接就立即自动执行定时任务
 * @Author: mzl
 * @CreateDate: 2020/12/19 3:10
 * @Version: 1.0
 */
@Component
public class StartListener implements CommandLineRunner {

    @Autowired
    private QuartzCronScheduler quartzCronScheduler;

    /**
     * 程序启动时就启动执行定时任务（实现了 CommandLineRunner的run（）启动执行方法）
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>定时任务开始执行<<<<<<<<<<<<<");
        quartzCronScheduler.scheduleJobs();    //执行启动定时任务
    }
}
