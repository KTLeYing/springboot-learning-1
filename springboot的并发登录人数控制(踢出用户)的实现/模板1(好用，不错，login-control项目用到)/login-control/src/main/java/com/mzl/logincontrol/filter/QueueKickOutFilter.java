package com.mzl.logincontrol.filter;

import com.mzl.logincontrol.pojo.CurrentUser;
import com.mzl.logincontrol.pojo.UserBO;
import org.redisson.api.RBucket;
import org.redisson.api.RDeque;
import org.redisson.api.RLock;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 踢出方式一：队列踢出方式
 */
public class QueueKickOutFilter extends KickOutFilter {
    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    @Override
    public boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("执行队列方式踢出...");
        String token = request.getHeader("Authorization");
        UserBO currentSession = CurrentUser.get();
        Assert.notNull(currentSession, "currentSession cannot null");
        String username = currentSession.getUsername();
        System.out.println("username" + username);
        String userKey = PREFIX + "deque_" + username;
        System.out.println("userKey:" + userKey);
        String lockKey = PREFIX_LOCK + username;
        System.out.println("lockKey:" + lockKey);

        //设置redission锁住对象,锁住当前的用户会话
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(2, TimeUnit.SECONDS);  //锁住的时间

        try {
            RDeque<String> deque = redissonClient.getDeque(userKey);

            // 如果队列里没有此token，且用户没有被踢出；放入队列
            if (!deque.contains(token) && currentSession.isKickout() == false) {
                deque.push(token);  //放入队列
            }

            System.out.println(deque.getFirst());
            System.out.println(deque.getLast());

            System.out.println("当前队列的大小：" + deque.size() );
            // 如果队列里的sessionId数超出最大会话数，开始踢人
            while (deque.size() > maxSession) {
                String kickoutSessionId;
                if (kickoutAfter) { // 如果踢出后者
                    System.out.println("踢出后者");
                    kickoutSessionId = deque.removeFirst();
                } else { // 否则踢出前者
                    System.out.println("踢出前者");
                    kickoutSessionId = deque.removeLast();
                }

                System.out.println("在队列中被选择踢出的用户为：" + kickoutSessionId);
                System.out.println("移除用户后的队列的大小：" + deque.size() );
                try {
                    RBucket<UserBO> bucket = redissonClient.getBucket(kickoutSessionId);
                    UserBO kickoutSession = bucket.get();
                    System.out.println("被选择踢出的用户是否在bucket存在：" + kickoutSession);

                    if (kickoutSession != null) {   //被选择踢出的对象在redisssion的bucket中，则踢出
                        System.out.println(kickoutSession + "用户被踢出了");
                        // 设置会话的kickout属性表示踢出了
                        kickoutSession.setKickout(true);
                        bucket.set(kickoutSession);
                    }
                } catch (Exception e) {
                }
            }

            // 如果是当前的对象被踢出了，直接退出，重定向到踢出后的地址
            if (currentSession.isKickout()) {
                // 当前线程会话会话被踢出了
                try {
                    // 注销（踢出用户）
                    userService.logout(token);
                    System.out.println("您的账号已在其他设备登录");
                    sendJsonResponse(response, 4001, "您的账号已在其他设备登录");
                } catch (Exception e) {
                }

                return false;
            }

        } finally {
            if (lock.isHeldByCurrentThread()) {  //如果会话上锁了则解锁
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + "==>unlock");
                LOGGER.info(Thread.currentThread().getName() + " unlock");
            } else {
                System.out.println(Thread.currentThread().getName() + "==>already automatically release lock");
                LOGGER.info(Thread.currentThread().getName() + " already automatically release lock");
            }
        }

        return true;
    }

}
