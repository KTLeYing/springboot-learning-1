package com.mzl.websocketdemo3.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName :   WebSocketServer
 * @Description: websocket的核心实现
 * @Author: mzl
 * @CreateDate: 2020/11/11 11:28
 * @Version: 1.0
 */
@Slf4j
@Component
@ServerEndpoint("/imserver/{userId}")
public class WebSocketServer {

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象,也可以直接存放session即可。key是用户id，value是当前的websocket对象，里面有session*/
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据,这个session在WebSocket类对象里面*/
    private Session session;
    /**userId*/
    private String userId="";

    /**
     * 连接成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws IOException {
        this.session = session;
        this.userId = userId;   //给当前的客户端赋值
        if (webSocketMap.containsKey(userId)){
            webSocketMap.remove(userId);
            webSocketMap.put(userId, this);  //this代表当前的websocket类，即当前的客户端对象，里面有session，相当于session，用户的标识
        }else {
            //加入到客户端set
            webSocketMap.put(userId, this);
            //在线人数加1
            addOnlineCount();
        }

        System.out.println("用户连接:" + userId + ",当前在线人数为:" + getOnlineCount());

        try{
            sendMessage("连接成功");
        } catch (Exception e) {
             System.out.println("用户:" + userId + ",网络异常!!!!!!");
            e.printStackTrace();
        }

    }

    /**
     * 服务器收到客户端消息后调用的方法，客户端在前端调用send（）方法后就会触发该函数
     * @param message  客户端发送过来的消息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("用户消息:" + userId + ",报文:"+message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotEmpty(message)){
            try{
                //解析发送的报文,JSON字符串解析为object
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人，防止串改
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                //传送给对应toUserId用户的websocket,发送到其他的要聊天的客户端上
                if (StringUtils.isNotEmpty(toUserId) && webSocketMap.containsKey(toUserId)){  //如果前端聊天的对方不为空而且对方在线
                    webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());  //服务器就会帮客户端发送消息给聊天的对方
                }else {
                    //否则不在这个服务器上，发送到mysql或者redis
                    System.out.println("请求的userId:" + toUserId + "不在该服务器上");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose(){
        if (webSocketMap.containsKey(userId)){
            //从set中删除会话
            webSocketMap.remove(userId);
           //在线人减1
            subOnlineCount();
        }
        System.out.println("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 出现错误时调用
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable){
        System.out.println("用户错误:" + this.userId + ",原因:" + throwable.getMessage());
        throwable.printStackTrace();
    }

    /**
     * 实现服务器主动推送给其他的用户
     * @param message
     */
    private void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }

    /**
     * 在线人数加1
     */
    public synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 在线人数减1
     */
    public synchronized void subOnlineCount() {
        onlineCount--;
    }

    /**
     * 获取在线人数
     * @return
     */
    public synchronized Integer getOnlineCount() {
        return onlineCount;
    }

    /**
     * 发送自定义的消息
     * @param message
     * @param userId
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        System.out.println("用户消息:" + userId + ",报文:"+message);
        if (StringUtils.isNotEmpty(userId) && webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);    //服务器就会帮客户端发送消息给聊天的对方
        }else{
            System.out.println("用户" + userId + ",不在线！");
        }
    }



}
