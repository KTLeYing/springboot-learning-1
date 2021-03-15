package com.mzl.scheduledemo1.schedulejob;

import com.mzl.scheduledemo1.sendemail.pojo.Mail;
import com.mzl.scheduledemo1.sendemail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @ClassName :   ScheduleJob
 * @Description: 定时任务类
 * @Author: mzl
 * @CreateDate: 2020/11/16 1:31
 * @Version: 1.0
 */
@Component
public class ScheduleJob {

    private final Logger logger = LoggerFactory.getLogger(Scheduled.class);

    @Autowired
    private MailService mailService;

  /**
   * 首先要去了解cron表达式 corn从左到右（用空格隔开）：秒 分 小时 月份中的日期 月份 星期中的日期 年份
   * 第一位，表示秒，取值0-59
   * 第二位，表示分，取值0-59
   * 第三位，表示小时，取值0-23
   * 第四位，日期天/日，取值1-31
   * 第五位，日期月份，取值1-12
   * 第六位，星期，取值1-7，1表示星期天，2表示星期一
   * 第七位，年份，可以留空，取值1970-2099
   *
   *●星号(*)：可用在所有字段中，表示对应时间域的每一个时刻，例如，*在分钟字段时，表示“每分钟”；
   *●问号（?）：该字符只在日期和星期字段中使用，它通常指定为“无意义的值”，相当于点位符；
   *●减号(-)：表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12；
   *●逗号(,)：表达一个列表值，如在星期字段中使用“MON,WED,FRI”，则表示星期一，星期三和星期五；
   *●斜杠(/)：x/y表达一个等步长序列，x为起始值，y为增量步长值。如在分钟字段中使用0/15，则表示为0,15,30和45秒，而5/15在分钟字段中表示5,20,35,50，你也可以使用，它等同于0/y；
   */
  // schedule ,定时任务,添加一个work()方法，每10秒执行一次。
  // 注意：当方法的执行时间超过任务调度频率时，调度器会在下个周期执行。
  // 如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第20秒。
  @Scheduled(cron = "0/10 * * * * *")
  public void work() {
        System.out.println("ScheduleJob-Cron: " + new Date());
  }


    // scheduleAtFixedRate ,固定频率， 按照指定频率执行任务，并以此规则开始周期性的执行调度。
    // 添加一个work()方法，每10秒执行一次。
    // 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第12秒。
    @Scheduled(fixedDelay = 1000*10)
    public void work1() {
        System.out.println("ScheduleJob-fixedDelay: " + new Date());
    }


    // fixedDelay 固定间隔任务,下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。
    // 举个栗子：
    // 添加一个work()方法，每隔10秒执行一次。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第22秒。
    @Scheduled(fixedRate = 1000*10)
    public void work2() {
        System.out.println("ScheduleJob-fixedRate: " + new Date());
    }


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
    @Scheduled(cron = "0 0 1 ? * 7")
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

    /**
     * 每天0,6,12，18时都执行一次
     */
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
//    @Scheduled(cron = "0/10 * * * * *")
    public void sendEmailSchedule(){
        Mail mail = new Mail();
        mail.setTos(new String[]{"2506173953@qq.com", "1574117930@qq.com", "2198902814@qq.com", "1836426588@qq.com", "1311562375@qq.com"});
//        mail.setTos(new String[]{"2198902814@qq.com", "2198902814@qq.com"});
        mail.setSubject("致大神");
        mail.setContent("记得登录岭师疫情防控系统打卡噢！！！(网址：http://47.112.252.171:8080/lingshiyqfkglsys/FrontWebServlet) 求大数据攻城狮带飞——请大神每天6、12、18和24时准时接收该邮件（邮件通知自动定时推送）");
        System.out.println(mail);
        mailService.sendCommonEmail(mail);
    }


}
