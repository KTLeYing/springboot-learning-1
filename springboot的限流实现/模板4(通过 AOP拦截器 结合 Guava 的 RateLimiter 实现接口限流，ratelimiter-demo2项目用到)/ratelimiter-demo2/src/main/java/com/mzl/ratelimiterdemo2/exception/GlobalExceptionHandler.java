package com.mzl.ratelimiterdemo2.exception;

import cn.hutool.core.lang.Dict;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName :   GlobalExceptionHandler
 * @Description: 全局异常处理器
 * @Author: mzl
 * @CreateDate: 2020/11/9 20:39
 * @Version: 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 监听运行时异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Dict handler(RuntimeException e){
        return Dict.create().set("msg", e.getMessage());   //利用hutool工具的数据返回传输对象，类似json的格式返回
    }

}
