package com.mzl.globalexceptiondemo2.utils;

import java.sql.ResultSet;
import java.util.Objects;

/**
 * @ClassName :   ResultUtils
 * @Description: 定义消息返回工具类，主要编写失败与成功的方法(一般与Result返回数据封装类一起用,在ResultUtils各方法中使用Result来封装返回结果，并
 * 把封装好了的信息的Result返回给前端)
 * 对于消息的返回，这是一个非常普通的工作，所以，我们可以将其封装一个工具类，能够进行有效代码的封装，减少多余的代码。
 * @Author: mzl
 * @CreateDate: 2020/10/13 21:34
 * @Version: 1.0
 */
public class ResultUtils {

    /**
     * 操作成功处理
     * @param object
     * @return
     */
    public static Result getSuccess(Object object){
        Result result = new Result();
        //设置成功的状态码
        result.setCode(200);
        //设置操作成功信息
        result.setMsg("成功");
        //设置返回的对象
        result.setData(object);
        return result;
    }

    /**
     * 重载返回成功的方法，因为有时候我们不需要任何的消息数据被返回
     * @return
     */
    public static Result getSuccess(){
        return getSuccess(null);
    }

    /**
     * 操作失败处理
     * @param code
     * @param msg
     * @param object 错误数据（其实这个一般都不需要的，因为都已经返回失败了，数据都没必要返回
     * @return
     */
    public static Result getError(Integer code, String msg, Object object){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    public static Result getError(Integer code, String msg){
        return getError(code, msg, null);
    }

}
