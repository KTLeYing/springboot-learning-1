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
 * 时间戳是指格林威治时间自1970年1月1日（00:00:00 GMT）至当前时间的总秒数。它也被称为Unix时间戳（Unix Timestamp）。通俗的讲，
 * 时间戳是一份能够表示一份数据在一个特定时间点已经存在的完整的可验证的数据。
 */
@Slf4j
@Component
public class UnixTimestampTest {


    /**
     * 获取时间戳的几种方法,java中，一般使用类型long,获取后原来默认是精确到毫秒，除以1000转换为精确到秒就可以方便我们操作了
     */
    public void getTimestamp() {
		//方法1
	    long nowTimestamp1 = System.currentTimeMillis() / 1000;
		System.out.println(nowTimestamp1);
		//方法2
	    long nowTimestamp2 = Calendar.getInstance().getTimeInMillis() / 1000;
		System.out.println(nowTimestamp2);
		//方法3
	    long nowTimestamp3 = new Date().getTime() / 1000;
		System.out.println(nowTimestamp3);
    }
	
	/**
     * 时间字符串格式转换为时间戳
     *
     * @param timeStr 时间字符串格式, eg. 2018-06-17 00:00:00
     * @return 时间戳
     * @throws Exception 时间格式解析错误
     */
    public static long time2timestamp(String timeStr) throws Exception {
        return time2timestamp(timeStr, DEFAULT_FORMAT);
    }

    /**
     * 时间字符串格式转换为时间戳
     *
     * @param timeStr 时间字符串格式, eg. 2018-06-17 00:00:00
     * @param timeFormat  时间格式
     * @return 时间戳
     * @throws Exception 时间格式解析错误
     */
    public static long time2timestamp(String timeStr, String timeFormat) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.parse(timeStr).getTime();
    }

    /**
     * 时间戳转换为时间字符串
     *
     * @param timestamp 时间戳, eg. 1513440360000L
     * @return 时间字符串格式
     */
    public static String timestamp2time(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FORMAT);
        return format.format(new Date(timestamp));
    }

    /**
     * 时间戳转换为时间字符串
     *
     * @param timestamp 时间戳, eg. 1513440360000L
     * @param timeFormat 时间格式
     * @return 时间字符串格式
     */
    public static String timestamp2time(long timestamp, String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(timestamp));
    }

    /** 默认时间格式: 2018-06-17 16:28:00 */
    private final static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";


     /**
     * 获取Timestamp类型的当前系统时间（原理：用long类型系统时间戳转换为timestamp类型的系统时间）
     *
     * @param timestamp 时间戳, eg. 1513440360000L
     * @param timeFormat 时间格式
     * @return 时间字符串格式
     */
     //法1：
    Timestamp d = new Timestamp(System.currentTimeMillis());
    //法2：
    Date date = new Date();       
    Timestamp nousedate = new Timestamp(date.getTime());
    //法3：
    Timestamp time3 = new Timestamp(Calendar.getInstance().getTimeInMillis());
	

}
