package com.mzl.scheduledemo1;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @ClassName :   TimerJob
 * @Description: 使用timer做定时任务，这个目前在项目中用的较少，与ScheduledExecutorService相似
 * @Author: mzl
 * @CreateDate: 2020/11/16 0:48
 * @Version: 1.0
 */
public class TimerJob {

  public static void main(String[] args) {
      TimerTask timerTask1 = new TimerTask() {
          @Override
          public void run() {
              System.out.println("Task is running - schedule：" + new Date());
          }
      };

      TimerTask timerTask2 = new TimerTask() {
          @Override
          public void run() {
              System.out.println("Task is running - scheduleAtFixedRate：" + new Date());
          }
      };

      // 参数：1、任务体 2、任务执行间隔 3、间隔时间单位

      Timer timer = new Timer();
      // schedule ,定时任务,添加一个work()方法，每10秒执行一次。
      // 注意：当方法的执行时间超过任务调度频率时，调度器会在下个周期执行。
      // 如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第20秒。
      timer.schedule(timerTask1, 10, 10000);

      // scheduleAtFixedRate ,固定频率， 按照指定频率执行任务，并以此规则开始周期性的执行调度。
      // 添加一个work()方法，每10秒执行一次。
      // 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
      // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第12秒。
      timer.scheduleAtFixedRate(timerTask2, 10, 10000);

  }

}
