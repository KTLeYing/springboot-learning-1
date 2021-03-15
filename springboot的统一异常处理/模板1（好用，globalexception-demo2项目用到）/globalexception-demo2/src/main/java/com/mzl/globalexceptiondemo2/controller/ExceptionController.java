package com.mzl.globalexceptiondemo2.controller;

import com.mzl.globalexceptiondemo2.exception.MyException;
import com.mzl.globalexceptiondemo2.utils.Result;
import com.mzl.globalexceptiondemo2.utils.ResultEnum;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   ExceptionController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/14 1:34
 * @Version: 1.0
 */
@RestController
public class ExceptionController {

    /**
     * 统一处理异常测试例子
     * @return
     */
    @RequestMapping("/test1")
    public Object test1(Integer num){
        if (num == null){
            throw new MyException(ResultEnum.ParameterException.getCode(), ResultEnum.ParameterException.getMsg());
        }

        int i = 10 / num;

        return "result is :" + i;
    }


}
