package com.mzl.activeuserdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName :   IndexController
 * @Description: 首页控制器
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:35
 * @Version: 1.0
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

}
