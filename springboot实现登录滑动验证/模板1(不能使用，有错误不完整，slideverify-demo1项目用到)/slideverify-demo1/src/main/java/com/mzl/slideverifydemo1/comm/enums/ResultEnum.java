package com.mzl.slideverifydemo1.comm.enums;

public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    ERROR(0,"失败"),
    SUCCESS(200,"成功")
    ;
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private  String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
