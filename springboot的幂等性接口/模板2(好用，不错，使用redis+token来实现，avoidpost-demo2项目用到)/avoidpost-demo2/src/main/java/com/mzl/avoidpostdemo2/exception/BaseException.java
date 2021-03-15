package com.mzl.avoidpostdemo2.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   BaseException
 * @Description: 自定义的异常类
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:08
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseException extends RuntimeException{

    private Integer code;

    private String msg;
}
