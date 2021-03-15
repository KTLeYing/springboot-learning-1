package com.mzl.quartzdemo1.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @ClassName :   QuartzJob
 * @Description: 简单的job任务（使用Scheduler 启动）
 * @Author: mzl
 * @CreateDate: 2020/12/19 1:38
 * @Version: 1.0
 */
@Slf4j
public class QuartzJob extends QuartzJobBean {

    private String name;

    public void setName(String name){
        this.name = name;
    }

    /**
     * 具体要执行的job定时任务
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("QuartzJon-------->{}", this.name);
        System.out.println("QuartzJob------>" + this.name);
    }
}
