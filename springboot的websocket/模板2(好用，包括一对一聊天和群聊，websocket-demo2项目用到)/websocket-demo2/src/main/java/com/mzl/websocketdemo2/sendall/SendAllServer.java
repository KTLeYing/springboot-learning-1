package com.mzl.websocketdemo2.sendall;

import com.google.gson.Gson;
import com.mzl.websocketdemo2.entity.Message;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName :   MyOneToOneServer
 * @Description: 一对一聊天
 * @Author: mzl
 * @CreateDate: 2020/11/10 21:36
 * @Version: 1.0
 */
@Component
@ServerEndpoint("/testAll/{userId}")
public class SendAllServer {

    /**
     * 用于存放所有在线客户端,每个客户端用户都有一个唯一的session，key是用户id，唯一标识，value是当前的websocket类对象，里面有session，
     * 或者value直接是session，session用来进行会话发送消息的，里面包括用户的所有各种信息的跟踪记录（与我们的以前的session有点不同）
     */
    private static Map<String, Session> clients = new ConcurrentHashMap<>();

    //Google的json工具
    private Gson gson = new Gson();

    /**
     * 创建连接调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") String userId){
        System.out.println("有新的客户端上线: " + session.getId());  //sessionId默认从0开始
        System.out.println(session);
        clients.put(userId, session);//储存key，key是用户id，用户的唯一标识来的，value是用户的session（与我们的以前的session有点不同，这是websocket的session）
    }

    /**
     * 关闭连接时调用
     * @param session
     */
    @OnClose
    public void onClose(Session session, @PathParam(value = "userId") String userId){
        System.out.println("有客户端离线: " + session.getId());
        clients.remove(userId);
    }

    /**
     * 出现错误是调用
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, @PathParam(value = "userId") String userId,Throwable throwable){
        if (clients.get(userId) != null){
            System.out.println("发生了错误,移除客户端: " + userId);
            clients.remove(userId);    //发生了错误，移除掉该用户session
        }
        throwable.printStackTrace();
    }

    /**
     * 服务器接收到客户端发送的消息后调用（客户端之前已经在前端调用send()函数来发送消息了），前端的send（）后就会触发此方法
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        System.out.println("收到客户端发来的消息: " + message);
        //服务器接收到刚刚客户端发来的消息后，发送推送消息给在线的其他所有的聊天的用户（好友），群聊
        this.sendAll(gson.fromJson(message, Message.class));   //this表示当前的session
    }

    /**
     * 发送消息，服务器接收到刚刚客户端发来的消息后，发送推送消息给在线的其他所有的聊天的用户（好友），群聊
     * @param message
     */
    public void sendAll(Message message){
        for (Map.Entry<String, Session> sessionEntry : clients.entrySet()){
            try{
                System.out.println("发送的消息数据：" + message);
                System.out.println(sessionEntry.getKey());
                System.out.println(sessionEntry.getValue());
                sessionEntry.getValue().getAsyncRemote().sendText(message.getMessage());  //同步发送消息，防止资源抢占，防止多个同时发送
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
