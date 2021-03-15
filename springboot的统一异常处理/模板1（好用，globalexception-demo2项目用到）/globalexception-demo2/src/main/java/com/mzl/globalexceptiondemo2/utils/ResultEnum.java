package com.mzl.globalexceptiondemo2.utils;

import lombok.*;
import org.omg.CORBA.SystemException;

/**
 * @EnumName :   ResultEnum1
 * @Description: 自定义一些返回状态码枚举类，便于本系统的使用(调用ResultUtils的方法，把对应的参数code和msg传给ResultUtils对应的方法处理，
 * 然后ResultUtils使用Result来封装信息并返回给前端)
 * @Author: mzl
 * @CreateDate: 2020/10/13 21:19
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultEnum {

    //成功：200(http中成功一般是200)
    SUCCESS(200, "成功"),

    //系统异常
    SystemException(-1, "系统异常"),

    //未知异常
    UnknownException(01, "未知异常"),

    //服务异常
    ServiceException(02, "服务异常"),

    //业务异常
    MyException(03, "业务异常"),

    //提示级异常
    InfoException(04, "提示级异常"),

    //数据库异常
    DBException(05, "数据库异常"),

    //参数验证异常
    ParameterException(06, "参数验证异常");


    private Integer code;
    private String msg;

}
