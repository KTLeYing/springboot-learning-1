package com.mzl.websocketdemo1.controller;

import com.mzl.websocketdemo1.config.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName :   WebsocketController
 * @Description: websocket控制器
 * @Author: mzl
 * @CreateDate: 2020/11/10 20:03
 * @Version: 1.0
 */
@Controller
public class WebsocketController {

    @Autowired
    private WebSocketServer webSocketServer;

    /**
     * 返回到index页面
     * @return
     */
    @GetMapping("index")
    public String index(){
        return "index";
    }

    @GetMapping("webSocket")
    public ModelAndView webSocket(){
        ModelAndView modelAndView = new ModelAndView("/webSocket");  //里面是viewName，相当于setViewName()函数
//        modelAndView.addObject("userId", userId);
        return modelAndView;
    }



}
