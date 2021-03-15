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
 * 描述：定时任务
 * 注意：这里可以通过接口调用定时任务或者设置系统自动定时
 *
 * @author 杨炜帆
 * @date : 2020/08/11
 */
@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    private ClockInService clockInService;

    /**
     * 测试定时任务,每天晚上24时执行一次，后面记得删除
	 * 方法可以自己定义，主要是在方法前使用了@Scheduled注解就行，注解里面是一个自己要设置的cron定时表达式，是自己设置定时的时间
	 * cron：通过表达式来配置任务执行时间(一般要用这个，常用)
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetTimes() {
        log.info("发起打卡剩余次数置0定时任务");
        clockInService.resetTimes(0);
    }
	
	 /**
     * fixedRate：定义一个按一定频率执行的定时任务（自己选择来用）
     */
	@Scheduled(fixedRate = 5000)
    public void scheduled1() {
        log.info("=====>>>>>使用fixedRate{}", System.currentTimeMillis());
    }

    /**
     * fixedDelay：定义一个按一定频率执行的定时任务，与上面不同的是，改属性可以配合initialDelay， 定义该任务延迟执行时间。（自己选择来用）
    */
    @Scheduled(fixedDelay = 5000)
    public void scheduled2() {
        log.info("=====>>>>>fixedDelay{}",System.currentTimeMillis());
    }
	

}
