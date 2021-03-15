package com.mzl.updateimage.result;

import com.mzl.updateimage.exception.SysError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;

/**
 * @ClassName :   Result
 * @Description: 返回结果封装
 * @Author: mzl
 * @CreateDate: 2021/1/2 11:43
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg){
        this.msg = msg;
    }

    /**
     * 操作成功返回
     * @param data
     * @return
     */
    public static Result success(Object data){
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 操作失败返回
     * @param data
     * @return
     */
    public static Result fail(Object data){
        return new Result(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg(), data);
    }


}
