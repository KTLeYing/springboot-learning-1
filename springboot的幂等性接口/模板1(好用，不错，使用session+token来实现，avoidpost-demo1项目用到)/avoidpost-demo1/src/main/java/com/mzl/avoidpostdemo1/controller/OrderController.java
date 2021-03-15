package com.mzl.avoidpostdemo1.controller;

import com.mzl.avoidpostdemo1.annotation.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName :   OrderController
 * @Description: 订单控制器
 * @Author: mzl
 * @CreateDate: 2020/11/22 15:53
 * @Version: 1.0
 */
@Controller
@RequestMapping("order")
public class OrderController {

    /***
     * 跳转到首页
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        System.out.println("跳转到首页面");
        return "index";
    }

    /**
     * 跳转到订单的页面
     * @return
     */
    @RequestMapping("/orderPage")
    @Token(generate = true)   //产生一个订单token
    public String orderPage(){
        System.out.println("跳转到了订单的页面");
        return "order";
    }

    /**
     * 提交订单后的成功的页面
     * @return
     */
    @RequestMapping("/submit")
    @Token(remove = true)
    public String submit(){
        System.out.println("你好，提交订单成功！");
        return "success";
    }




}
