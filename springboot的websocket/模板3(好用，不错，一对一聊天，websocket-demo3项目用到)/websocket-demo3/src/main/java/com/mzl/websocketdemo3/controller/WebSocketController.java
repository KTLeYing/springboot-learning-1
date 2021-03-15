package com.mzl.websocketdemo3.controller;

import com.mzl.websocketdemo3.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.server.PathParam;
import java.io.IOException;

/**
 * @ClassName :   WebSocketController
 * @Description: websocket控制器
 * @Author: mzl
 * @CreateDate: 2020/11/11 15:03
 * @Version: 1.0
 */
@Controller
public class WebSocketController {

    /**
     * 请求测试
     * @return
     */
    @GetMapping("index")
    public ResponseEntity<String> index(){
        return ResponseEntity.ok("请求成功");
    }

    /**
     * websocket测试
     * @return
     */
    @GetMapping("websocket")
    public ModelAndView websocket(){
        return new ModelAndView("websocket");
    }

    @GetMapping("push/{toUserId}")
    public ResponseEntity<String> pushToWeb(String message, @PathVariable("toUserId") String toUserId) throws IOException {
        WebSocketServer.sendInfo(message, toUserId);
        return ResponseEntity.ok("发送消息成功...");
    }



}
