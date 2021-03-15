package com.mzl.scheduledemo1;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   ScheduledExecutorJob
 * @Description: 该方法跟Timer类似，直接看demo，比较少用
 * @Author: mzl
 * @CreateDate: 2020/11/16 0:56
 * @Version: 1.0
 */
public class ScheduledExecutorServiceJob {

    public static void main(String[] args){
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    // 参数：1、任务体 2、首次执行的延时时间 3、任务执行间隔 4、间隔时间单位

    // schedule ,定时任务,添加一个work()方法，每10秒执行一次。
    // 注意：当方法的执行时间超过任务调度频率时，调度器会在下个周期执行。
    // 如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第20秒。
    scheduledExecutorService.schedule(
        () -> System.out.println("Task ScheduledExecutorService-schedule： " + new Date()),
        10,
        TimeUnit.SECONDS);

    // scheduleAtFixedRate ,固定频率， 按照指定频率执行任务，并以此规则开始周期性的执行调度。
    // 添加一个work()方法，每10秒执行一次。
    // 注意：当方法的执行时间超过任务调度频率时，调度器会在当前方法执行完成后立即执行下次任务。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第12秒。
    scheduledExecutorService.scheduleAtFixedRate(
        () ->
            System.out.println("Task ScheduledExecutorService-scheduleAtFixedRate： " + new Date()),
        0,
        10,
        TimeUnit.SECONDS);

    // fixedDelay 固定间隔任务,下一次的任务执行时间，是从方法最后一次任务执行结束时间开始计算。并以此规则开始周期性的执行任务。
    // 举个栗子：
    // 添加一个work()方法，每隔10秒执行一次。
    // 例如：假设work()方法在第0秒开始执行，方法执行了12秒，那么下一次执行work()方法的时间是第22秒。
    scheduledExecutorService.scheduleWithFixedDelay(
        () ->
            System.out.println(
                "Task ScheduledExecutorService-scheduleWithFixedDelay： " + new Date()),
        0,
        10,
        TimeUnit.SECONDS);
    }
}
