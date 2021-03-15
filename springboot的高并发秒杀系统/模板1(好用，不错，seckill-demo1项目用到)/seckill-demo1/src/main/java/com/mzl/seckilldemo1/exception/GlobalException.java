package com.mzl.seckilldemo1.exception;

import lombok.Data;

/**
 * @ClassName :   GlobalException
 * @Description: 全局自定义异常
 * @Author: mzl
 * @CreateDate: 2021/3/3 9:02
 * @Version: 1.0
 */
@Data
public class GlobalException extends RuntimeException{

    private static final long servialVersionUID = 1L;

    private Integer code;
    private String msg;

    public GlobalException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

}
