package com.mzl.slideverifydemo1.comm.entity;

import com.mzl.slideverifydemo1.comm.enums.ResultEnum;

import java.io.Serializable;


public class ReturnT<T> implements Serializable {
    public static final long serialVersionUID = 1L;
/*
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT<String>(null);
    public static final ReturnT<String> FAIL = new ReturnT<String>(FAIL_CODE, null);
*/
    private int code;
    private String msg;
    private T data;

    public ReturnT(){}
    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ReturnT(T data) {
        this.code =  ResultEnum.SUCCESS.getCode();
        this.msg =  ResultEnum.SUCCESS.getMsg();
        this.data = data;
    }
    public ReturnT(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
