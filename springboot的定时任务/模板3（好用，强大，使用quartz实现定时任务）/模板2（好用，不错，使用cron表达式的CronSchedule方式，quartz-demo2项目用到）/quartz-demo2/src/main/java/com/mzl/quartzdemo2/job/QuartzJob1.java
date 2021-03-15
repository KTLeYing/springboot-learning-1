package com.mzl.quartzdemo2.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   QuartzJob1
 * @Description: 定时任务1（使用CronSchedule表达式方式）
 * @Author: mzl
 * @CreateDate: 2020/12/19 2:27
 * @Version: 1.0
 */
public class QuartzJob1 implements Job {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        System.out.println("CronSchedule------->schedule job【1】 is running..." + name + "-------->" + simpleDateFormat.format(new Date()));
    }

}
