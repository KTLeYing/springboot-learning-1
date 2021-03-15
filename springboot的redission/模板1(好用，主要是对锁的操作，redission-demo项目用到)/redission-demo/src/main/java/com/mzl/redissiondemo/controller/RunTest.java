package com.mzl.redissiondemo.controller;

import javafx.application.Application;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.xml.ws.Action;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @ClassName :   RunTest
 * @Description: 线程启动测试类（此测试类默认启动这个程序的应用）
 * @Author: mzl
 * @CreateDate: 2021/1/24 14:15
 * @Version: 1.0
 */
public class RunTest implements ApplicationRunner {

    //创建日志对象
    private static final Logger LOGGER = LoggerFactory.getLogger(RunTest.class);

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 重写ApplicationRunner的run方法，启动方法
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            //创建线程列
            Thread thread = new Thread("Thread-" + i);
            thread.start(); //启动线程
            Thread.sleep(100);   //线程休眠
        }
    }

    /**
     * 内部类，线程Thread测试类
     */
    private class TestThread extends Thread{
        public TestThread(String name){
            super(name);
        }

        /**
         * 重写Thread的run方法
         */
        @Override
        public void run() {
            RLock lock = redissonClient.getLock("lock");
            //创建公平锁对象
            RLock lock1 = redissonClient.getFairLock("lock");
            LOGGER.info(Thread.currentThread().getName() + " tryLock");
            //获取锁
            lock1.lock(2, SECONDS);

            //当前线程是保持此锁
            if (lock1.isHeldByCurrentThread()){
                try{
                    LOGGER.info(Thread.currentThread().getName() + " getLock");
                    Thread.sleep(5000);
                    LOGGER.info(Thread.currentThread().getName() + " 业务执行完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    //如果当前线程保持此锁
                    if (lock1.isHeldByCurrentThread()){
                        //当前线程解锁
                        lock1.unlock();
                        LOGGER.info(Thread.currentThread().getName() + " unlock");
                    }else {
                        LOGGER.info(Thread.currentThread().getName() + " already automatically release lock");
                    }
                }
            }else {   //当前线程没有保持此锁,就什么没有获取锁成功
                LOGGER.info(Thread.currentThread().getName() + " lock fail");
            }
        }
    }

}
