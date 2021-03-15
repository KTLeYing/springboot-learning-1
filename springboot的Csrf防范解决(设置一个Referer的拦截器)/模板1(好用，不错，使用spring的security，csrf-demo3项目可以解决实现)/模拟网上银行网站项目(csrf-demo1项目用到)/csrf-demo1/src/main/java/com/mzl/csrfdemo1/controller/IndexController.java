package com.mzl.csrfdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   IndexController
 * @Description: 测试首页控制器(模拟网上银行网站)
 * @Author: mzl
 * @CreateDate: 2021/3/11 15:56
 * @Version: 1.0
 */
@Controller
public class IndexController {

    /**
     * 跳转到首页面
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    /**
     * 模拟银行转账
     * @param name
     * @param money
     * @return
     */
    @RequestMapping("/transferMoney")
    @ResponseBody
    public String transferMoney(String name, Integer money){
        String msg = name + "转账" + money + "元成功";
        System.out.println(msg);
        return msg;
    }


}
