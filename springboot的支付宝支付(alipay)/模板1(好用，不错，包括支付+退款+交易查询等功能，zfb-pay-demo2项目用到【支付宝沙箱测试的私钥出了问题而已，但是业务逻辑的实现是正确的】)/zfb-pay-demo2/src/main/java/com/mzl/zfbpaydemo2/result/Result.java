package com.mzl.zfbpaydemo2.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.soap.SAAJResult;

/**
 * @ClassName :   Result
 * @Description: 返回的结果封装
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:46
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    /**
     * 成功返回
     * @param msg
     * @param data
     * @return
     */
    public static Result ok(String msg, Object data){
        return new Result(ResultEnum.OK.getCode(), msg, data);
    }

    public static Result ok(){
        return ok(null, null);
    }

    /**
     * 失败返回
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg){
        return error(code, msg, null);
    }

    public static  Result error(Integer code, String msg, Object data){
        return new Result(code, msg, data);
    }

    public static  Result error(ResultEnum resultEnum){
        return error(resultEnum, null);
    }

    public static  Result error(ResultEnum resultEnum, Object data){
        return error(resultEnum.getCode(), resultEnum.getMsg(), data);
    }

}
