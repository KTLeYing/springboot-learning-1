package com.mzl.seckilldemo1.limit;

import com.google.common.util.concurrent.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName :   RateLimiterDemo
 * @Description: 限流的演示
 * @Author: mzl
 * @CreateDate: 2021/3/6 11:55
 * @Version: 1.0
 */
public class RateLimiterDemo {

    public static void main(String[] args){
        RateLimiter rateLimiter = RateLimiter.create(2);
        List<Runnable> tasks = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            tasks.add(new UserRequest(i));
        }
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Runnable runnable : tasks){
            System.out.println("等待时间：" + rateLimiter.acquire());  //线程限流
            //线程池执行线程
            threadPool.execute(runnable);
        }
    }

    private static class UserRequest implements Runnable{

        private int id;

        public UserRequest(int id){
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println(id);
        }
    }

}
