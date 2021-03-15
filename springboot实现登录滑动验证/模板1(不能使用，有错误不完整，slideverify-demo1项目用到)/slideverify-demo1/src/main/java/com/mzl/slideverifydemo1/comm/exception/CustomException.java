package com.mzl.slideverifydemo1.comm.exception;

import com.mzl.slideverifydemo1.comm.enums.ResultEnum;

public class CustomException extends RuntimeException {
    private Integer code;
    public CustomException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public CustomException(Integer code, String message) {
        super(message);//父类本身就有message
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
