package com.mzl.zfbpaydemo2.exception;

import com.mzl.zfbpaydemo2.result.ResultEnum;
import lombok.Data;

/**
 * @ClassName :   CustomException
 * @Description: 自定义的异常
 * @Author: mzl
 * @CreateDate: 2021/2/25 11:08
 * @Version: 1.0
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    /**
     * 自定义的异常构造方法
     * @param resultEnum
     */
    public CustomException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    /**
     * 自定义的异常构造方法
     * @param code
     * @param msg
     */
    public CustomException(Integer code, String msg){
        super(msg);
        this.code = code;
    }


}
