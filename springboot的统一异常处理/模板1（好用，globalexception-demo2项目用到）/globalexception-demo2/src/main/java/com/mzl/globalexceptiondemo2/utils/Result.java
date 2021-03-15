package com.mzl.globalexceptiondemo2.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResultUtils
 * @Description: 返回数据的包装类（一般与返回结果工具类一起用,因为ResultUtils中使用Result来封装返回的结果并把封装好了的信息的Result返回给前端）
 * @Author: mzl
 * @CreateDate: 2020/10/13 21:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    //返回的状态码
    private Integer code;
    //返回的消息
    private String msg;
    //返回的数据
    private T data;

}
