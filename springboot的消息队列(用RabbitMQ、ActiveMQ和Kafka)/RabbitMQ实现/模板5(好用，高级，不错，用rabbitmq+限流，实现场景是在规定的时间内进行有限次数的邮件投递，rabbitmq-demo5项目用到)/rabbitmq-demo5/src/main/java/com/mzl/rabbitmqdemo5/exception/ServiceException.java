package com.mzl.rabbitmqdemo5.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ServiceException
 * @Description: 自定义统一业务逻辑异常处理
 * @Author: mzl
 * @CreateDate: 2020/10/23 8:54
 * @Version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ServiceException extends RuntimeException{

    private String code;
    private String msg;

    public ServiceException(String msg) {
        this.msg = msg;
    }

}
