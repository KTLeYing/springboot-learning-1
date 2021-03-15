package com.fsl.springbootschedule.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sj2
 * 定时任务
 */
@Component
public class ScheduleJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 首先要去了解cron表达式
     * corn从左到右（用空格隔开）：秒 分 小时 月份中的日期 月份 星期中的日期 年份
     * 第一位，表示秒，取值0-59
     * 第二位，表示分，取值0-59
     * 第三位，表示小时，取值0-23
     * 第四位，日期天/日，取值1-31
     * 第五位，日期月份，取值1-12
     * 第六位，星期，取值1-7，1表示星期天，2表示星期一
     * 第七位，年份，可以留空，取值1970-2099
     */
    /**
     * 每天下午16点33分开始执行
     */
    @Scheduled(cron = "0 33 16 * * ?")
    public void run1() {
        logger.info(">>>>> cron下午16:33上传检查开始....");
        logger.info(">>>>> cron下午16:33上传检查结束....");
    }

    /**
     * 每隔五秒完成一次
     */
    @Scheduled(cron="0/5 * *  * * ? ")
    public void run2(){
        logger.info(">>>>> cron每隔五秒检查开始....");
        logger.info(">>>>> cron每隔五秒检查结束....");
    }

    /**
     * 容器启动的时候执行一次，之后每隔10分钟执行一次
     */
    @Scheduled(initialDelay=1000, fixedRate=10*60*1000)
    public void run3(){
        logger.info(">>>>> cron项目启动的时候执行一次，之后每隔10分钟执行一次开始....");
        logger.info(">>>>> cron项目启动的时候执行一次，之后每隔10分钟执行一次结束....");
    }


    /**
     *  上一次执行完毕时间点之后5秒再执行
     *  fixedDelay 总是前一次任务完成后，延时固定长度然后执行一次任务。
     */
    @Scheduled(fixedDelay = 5000)
    public void run4(){
        logger.info(">>>>> cron项目fixedDelay注解执行开始....");
        logger.info(">>>>> cron项目fixedDelay注解执行结束....");
    }


    /**
     *  上一次执行完毕时间点之后5秒再执行
     *  fixedDelay 总是前一次任务完成后，延时固定长度然后执行一次任务。
     */
    @Scheduled(fixedDelayString = "5000")
    public void run5(){
        logger.info(">>>>> cron项目fixedDelay注解执行开始....");
        logger.info(">>>>> cron项目fixedDelay注解执行结束....");
    }


    /**
     *  上一次执行完毕时间点之后5秒再执行
     *  fixedDelay 总是前一次任务完成后，延时固定长度然后执行一次任务。
     */
    @Scheduled(fixedDelayString = "${spring.schedule.fixedDelayString}")
    public void run6(){
        logger.info(">>>>> cron项目fixedDelayString占位符注解执行开始....");
        logger.info(">>>>> cron项目fixedDelayString占位符注解执行结束....");
    }

    /**
     *  间隔五秒之后执行
     *  fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，判断是否到时机执行。
     *  fixedRate 的任务某次执行时间再长也不会造成两次任务实例同时执行，除非用了 @Async 注解。
     */
    @Scheduled(fixedRate = 5000)
    public void run7(){
        logger.info(">>>>> cron项目fixedRate注解执行开始....");
        logger.info(">>>>> cron项目fixedRate注解执行结束....");
    }

    /**
     *  间隔五秒之后执行
     *  fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，判断是否到时机执行。
     *  fixedRate 的任务某次执行时间再长也不会造成两次任务实例同时执行，除非用了 @Async 注解。
     */
    @Scheduled(fixedRateString = "5000")
    public void run9(){
        logger.info(">>>>> cron项目fixedRateString注解执行开始....");
        logger.info(">>>>> cron项目fixedRateString注解执行结束....");
    }

    /**
     *  间隔五秒之后执行
     *  fixedRate 每次任务结束后会从任务编排表中找下一次该执行的任务，判断是否到时机执行。
     *  fixedRate 的任务某次执行时间再长也不会造成两次任务实例同时执行，除非用了 @Async 注解。
     */
    @Scheduled(fixedRateString = "${spring.schedule.fixedRateString}")
    public void run8(){
        logger.info(">>>>> cron项目fixedRateString占位符注解执行开始....");
        logger.info(">>>>> cron项目fixedRateString占位符注解执行结束....");
    }


    /**
     * 每隔一分钟执行一次
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void run10(){
        logger.info(">>>>> cron项目每隔一分钟执行一次执行开始....");
        logger.info(">>>>> cron项目每隔一分钟执行一次执行结束....");
    }

    /**
     * 每天23点执行一次
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void run11(){
        logger.info(">>>>> cron项目每天23点执行一次执行开始....");
        logger.info(">>>>> cron项目每天23点执行一次执行结束....");
    }


    /**
     * 每月1号凌晨1点执行一次
     */
    @Scheduled(cron = "0 0 1 1 * ?")
    public void run12(){
        logger.info(">>>>> cron项目每月1号凌晨1点执行一次执行开始....");
        logger.info(">>>>> cron项目每月1号凌晨1点执行一次执行结束....");
    }


    /**
     * 每月最后一天23点执行一次
     */
    @Scheduled(cron = "0 0 23 L * ?")
    public void run13(){
        logger.info(">>>>> cron项目每月最后一天23点执行一次执行开始....");
        logger.info(">>>>> cron项目每月最后一天23点执行一次执行结束....");
    }


    /**
     * 每周星期天凌晨1点实行一次
     */
    @Scheduled(cron = "0 0 1 ? * L")
    public void run14(){
        logger.info(">>>>> cron项目每周星期天凌晨1点实行一次执行开始....");
        logger.info(">>>>> cron项目每周星期天凌晨1点实行一次执行结束....");
    }


    /**
     * 每天的0点、13点、18点、21点都执行一次
     */
    @Scheduled(cron = "0 0 0,13,18,21 * * ?")
    public void run15(){
        logger.info(">>>>> cron项目每天的0点、13点、18点、21点都执行一次执行开始....");
        logger.info(">>>>> cron项目每天的0点、13点、18点、21点都执行一次执行结束....");
    }

}
