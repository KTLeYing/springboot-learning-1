package com.mzl.crossingdemo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   OtherTestController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/26 12:02
 * @Version: 1.0
 */
@RestController
public class OtherTestController {

    /**
     * 其他跨域配置类测试接口（通用）
     * @return
     */
    @RequestMapping("/otherTest")
    public String otherTest(){
        return "success";
    }

}
