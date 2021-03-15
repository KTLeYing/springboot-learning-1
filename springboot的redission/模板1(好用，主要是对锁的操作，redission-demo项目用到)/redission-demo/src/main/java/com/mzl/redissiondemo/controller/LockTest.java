package com.mzl.redissiondemo.controller;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   LockTest
 * @Description: 线程用户锁测试
 * @Author: mzl
 * @CreateDate: 2021/1/24 14:15
 * @Version: 1.0
 */
public class LockTest {

    /**
     * 先执行主线程，再执行内部的次线程（主线程优先级大于次线程），在对方休眠或者获取锁花费时间时可以回去执行其他的线程
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        //配置redis的地址服务器
        config.useSingleServer().setAddress("redis://localhost:6379");
        //创建redis的会话
        RedissonClient redissonClient = Redisson.create(config);

        //获取对会话test用户的对象锁（主线程）
        RLock lock = redissonClient.getLock("test");
        lock.tryLock(10, 10, TimeUnit.SECONDS);

    // 创建并启动一个线程(次线程)
    new Thread(
            () -> {
              System.out.println("执行次线程逻辑");
              try {
                // 获取锁时间为10秒
                System.out.println("次线程即将开始获取锁，然后可以回去执行主线程了");
                lock.tryLock(10, 10, TimeUnit.SECONDS);
                System.out.println("次线程获得锁了");
              } catch (Exception e) {
                e.printStackTrace();
              }
              System.out.println("👴完事了");
              // 操作完了开始解锁,释放锁，让其他的线程用户获取
              lock.unlock();
              System.out.println("次线程解锁");
            })
        .start();

        try{
            System.out.println("执行主线程逻辑");
            System.out.println("主线程开始休息了，可以执行其他的线程");
            TimeUnit.SECONDS.sleep(5);  //主线程休息5秒（这时执行去执行其他线程）
            System.out.println("主线程休息完了，又继续执行主线程");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //主线程操作完了，解锁，释放锁
        lock.unlock();
        System.out.println("主线程解锁");
    }

}
