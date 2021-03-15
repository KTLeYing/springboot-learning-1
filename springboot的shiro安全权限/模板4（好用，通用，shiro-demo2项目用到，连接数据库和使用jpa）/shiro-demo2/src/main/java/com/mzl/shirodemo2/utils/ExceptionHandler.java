package com.mzl.shirodemo2.utils;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.ResultSet;

/**
 * @ClassName :   Exception
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/22 0:16
 * @Version: 1.0
 */
@RestControllerAdvice
public class ExceptionHandler {
    @Autowired
    private ResultMap resultMap;

    // 捕捉 CustomRealm 抛出的异常
    public ResultMap handleShiroException(Exception ex){
        return resultMap.fail().message(ex.getMessage());
    }

}
