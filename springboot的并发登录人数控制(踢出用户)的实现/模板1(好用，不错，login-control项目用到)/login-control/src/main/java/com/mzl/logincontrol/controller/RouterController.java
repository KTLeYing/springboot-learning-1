package com.mzl.logincontrol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    /**
     * 跳转到登录页面
     * @return
     */
    @GetMapping("/toLogin")
    public String login() {
        return "login";
    }

    /**
     * 首页面
     * @return
     */
    @GetMapping("/toIndex")
    public String index() {
        return "index";
    }

    /**
     *启动后默认去登录页面
     * @return
     */
    @GetMapping("/")
    public String _login() {
        return "login";
    }
}
