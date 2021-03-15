package com.mzl.globalexceptiondemo1.controller;

import com.mzl.globalexceptiondemo1.exception.MyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   ExceptionTestController
 * @Description: 全局异常测试控制器
 * @Author: mzl
 * @CreateDate: 2020/10/11 20:41
 * @Version: 1.0
 */
@RestController
public class ExceptionTestController {

    /**
     * 测试例子1
     * @param num
     * @return
     */
    @RequestMapping("/test1")
    public String test1(Integer num){
        if (num == null){    //自定义异常要手动抛出
            throw new MyException(400, "num不能为空");
        }

        int i = 10 / num;   //运行时异常不用手动抛出，自动捕获
        return "result is :" + i;
    }

}
