package com.mzl.slideverifydemo1.comm.controller;

import com.mzl.slideverifydemo1.comm.entity.ReturnT;
import com.mzl.slideverifydemo1.comm.enums.ResultEnum;
import com.mzl.slideverifydemo1.comm.exception.CustomException;
import com.mzl.slideverifydemo1.comm.util.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle extends BaseController {
    @ExceptionHandler(value = Exception.class)//声明需要捕获的异常类 - 写成PeopleException，就是只会捕获PeopleException异常了
    @ResponseBody //由于返回浏览器那边是json格式，就需要这个
    public ReturnT handle(Exception e){
        if(e instanceof CustomException){
            CustomException cusException = (CustomException) e;
            return ResultUtil.error(cusException.getCode(),cusException.getMessage());
        }else {
            logger.error("[系统异常]-{}",e);
            return ResultUtil.normal(ResultEnum.UNKONW_ERROR);
        }
    }
}
