package com.mzl.scheduledemo1.schedulejob;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName :   ScheduleJob
 * @Description: 定时任务类
 * @Author: mzl
 * @CreateDate: 2020/11/16 1:31
 * @Version: 1.0
 */
@Component
public class ScheduleJob1 {

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
  @Async
  public void work() {
        System.out.println("ScheduleJob1-Cron: " + new Date());
  }


    // scheduleAtFixedRate ,固定频率， 按照指定频率执行任务，并以此规则开始周期性的执行调度。
    // 添加一个work()方法，每10秒执行一次。
    // 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第12秒。
    @Scheduled(fixedDelay = 1000*10)
    @Async
    public void work1() {
        System.out.println("ScheduleJob1-fixedDelay: " + new Date());
    }


    // fixedDelay 固定间隔任务,下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。
    // 举个栗子：
    // 添加一个work()方法，每隔10秒执行一次。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第22秒。
    @Scheduled(fixedRate = 1000*10)
    @Async
    public void work2() {
        System.out.println("ScheduleJob1-fixedRate: " + new Date());
    }
}
