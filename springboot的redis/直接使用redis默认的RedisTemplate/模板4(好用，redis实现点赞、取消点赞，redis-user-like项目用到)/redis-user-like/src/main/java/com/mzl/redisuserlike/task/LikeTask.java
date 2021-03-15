package com.mzl.redisuserlike.task;

import com.mzl.redisuserlike.service.LikeService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName :   LikeTask
 * @Description: 点赞的定时任务，定时将redis的所有的点赞的记录持久化保存到数据库中
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:44
 * @Version: 1.0
 */
@Slf4j
public class LikeTask extends QuartzJobBean {

    @Autowired
    private LikeService likeService;

    //时间格式化
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 执行定时任务的核心方法
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("LikeTask--------{}", simpleDateFormat.format(new Date()));
        //将redis中的点赞信息同步存储到数据库中
        likeService.saveLikedFromRedis();
        likeService.saveLikedCountFromRedis();
    }
}
