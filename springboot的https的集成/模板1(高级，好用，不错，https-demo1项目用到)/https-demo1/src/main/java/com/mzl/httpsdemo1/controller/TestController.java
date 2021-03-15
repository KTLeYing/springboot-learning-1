package com.mzl.httpsdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName :   TestController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/10 1:34
 * @Version: 1.0
 */
@Controller
public class TestController {

    /**
     * 测试1（使用页面跳转）
     * @return
     */
    @RequestMapping("test1")
    public String test1(){
        return "index";
    }

    /**
     * 测试2（http测试工具）
     * @return
     */
    @RequestMapping("test2")
    @ResponseBody
    public String test2(){
        return "MZL";
    }

}
