package com.mzl.avoidpostdemo2.controller;

import com.mzl.avoidpostdemo2.annotation.AutoIdempotent;
import com.mzl.avoidpostdemo2.result.ResultMap;
import com.mzl.avoidpostdemo2.service.TestService;
import com.mzl.avoidpostdemo2.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   TestController
 * @Description: 测试控制器
 * @Author: mzl
 * @CreateDate: 2020/11/22 20:46
 * @Version: 1.0
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;
    @Autowired
    private TokenService tokenService;

    /**
     * 生成token的接口
     * @return
     */
    @RequestMapping("getToken")
    public ResultMap getToken(){
        String token = tokenService.createToken();
        return new ResultMap(1001, "创建token成功", token);
    }

    /**
     * 相当于添加数据接口（测试时 连续点击添加数据按钮  看结果是否是添加一条数据还是多条数据）
     */
    @RequestMapping("submit")
    @AutoIdempotent
    public ResultMap submit(){
        String s = testService.testMethod();
        return new ResultMap(1002, "提交成功", s);
    }

}
