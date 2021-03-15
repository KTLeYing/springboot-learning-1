package com.mzl.websocketdemo1.config;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName :   WebSocketServer
 * @Description: websocket服务器
 * @Author: mzl
 * @CreateDate: 2020/11/10 19:23
 * @Version: 1.0
 */
@Component
@ServerEndpoint("/webSocket/{sid}")   //前端js的socket方式发送的请求路径
public class WebSocketServer {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineNum = new AtomicInteger();

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocketServer对象(即原来存储session，某个客户端都有一个对应的session用于消息会话，
    // 与我们以前的session不同,这是websocket的session
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();

    /**
     * 发送消息
     * @param session
     * @param message
     * @throws Exception
     */
    public void sendMessage(Session session, String message) throws Exception{
        if (session != null){
            synchronized (session){  //同步锁，防止资源抢占，服务器会推送消息给多个人，群聊
                System.out.println("发送的数据：" + message);
                session.getBasicRemote().sendText(message);   //发送消息
            }
        }
    }

    /**
     * 给指定用户发送信息
     * @param username
     * @param message
     */
    public void sendInfo(String username, String message){
        Session session = sessionPools.get(username);
        try{
            sendMessage(session, message);  //发送消息
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立连接成功调用，浏览器自动发送客户端session，前端socket.onopen（）时会调用这个，建立连接
     * @param session
     * @param username
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sid") String username){
        //key是用户id，唯一标识，value是当前的websocket类对象，里面有session，或者value直接是session，session用来进行会话发送消息的，
        // 里面包括用户的所有各种信息的跟踪记录（与我们的以前的session有点不同,这是websocket的session）
        sessionPools.put(username, session);
        System.out.println(session);
        //在线人数加1
        addOnlineCount();
        System.out.println(username + "加入webSocket！当前人数为" + onlineNum);
        try{
            sendMessage(session, "欢迎" + username + "加入连接！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务器接收到客户端发送的消息调用（即客户端发送消息后，调用send()函数后），前端的send（）发送后就会触发此方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message) throws Exception{
        message = "客户端：" + message + ",已收到";
        System.out.println(message);
        for (Map.Entry<String, Session> sessionMap : sessionPools.entrySet()) {    //服务器接收到客户端的消息后，发送推送消息给所有的的在线的聊天的其他客户，群聊
            System.out.println(sessionMap.getKey());
            System.out.println(sessionMap.getValue());
            try{
                //回复收到刚刚客户端的信息
                sendMessage(sessionMap.getValue(), message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 出现错误时调用
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("出现错误...");
        throwable.printStackTrace();
    }

    /**
     * 关闭连接时调用，前端调用socket.close()时调用
     * @param username
     */
    @OnClose
    public void onClose(@PathParam(value = "sid") String username){
        //移除用户session
        sessionPools.remove(username);
        subOnlineCount();
        System.out.println(username + "断开webSocket连接！当前人数为" + onlineNum);
    }

    /**
     * 在线数量增加
     */
    public void addOnlineCount() {
        onlineNum.incrementAndGet();
    }

    /**
     * 在线数量减少
     */
    public void subOnlineCount() {
        onlineNum.decrementAndGet();
    }

}
