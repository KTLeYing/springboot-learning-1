package com.mzl.updateimage.exception;

/**
 * @ClassName :   BaseException
 * @Description: 通用的自定义异常类型
 * @Author: mzl
 * @CreateDate: 2021/1/2 11:34
 * @Version: 1.0
 */
public class BaseException extends RuntimeException {

    private final SysError sysError;

    public BaseException(){
        this.sysError = SysError.SYSTEM_ERROR;
    }

    public BaseException(SysError sysError) {
        super(sysError.getMsg());
        this.sysError = sysError;
    }

    public BaseException(SysError sysError, String msg){
        super(msg);
        this.sysError = sysError;
    }

    public BaseException(SysError sysError, String msg, Throwable cause){
        super(msg, cause);
        this.sysError = sysError;
    }

}
