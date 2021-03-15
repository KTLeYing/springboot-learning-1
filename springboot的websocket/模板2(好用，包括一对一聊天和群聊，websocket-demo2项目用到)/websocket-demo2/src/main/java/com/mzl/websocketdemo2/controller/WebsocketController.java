package com.mzl.websocketdemo2.controller;

import com.mzl.websocketdemo2.sendone.MyOneToOneServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName :   WebsocketController
 * @Description: websocket控制器
 * @Author: mzl
 * @CreateDate: 2020/11/10 22:16
 * @Version: 1.0
 */
@Controller
public class WebsocketController {

    @Autowired
    private MyOneToOneServer myOneToOneServer;

    /**
     * 一对一聊天
     * @return
     */
    @GetMapping("oneToOne")
    public ModelAndView oneToOne(){
        ModelAndView modelAndView = new ModelAndView("/onetoone");
        return modelAndView;
    }

    /**
     * 群聊天
     * @return
     */
    @GetMapping("sendAll")
    public ModelAndView sendAll(){
        ModelAndView modelAndView = new ModelAndView("/sendall");
        return modelAndView;
    }


}
