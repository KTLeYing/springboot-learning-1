package com.mzl.rabbitmqdemo5.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   ServerResponse
 * @Description: 数据响应到前端的封装类
 * @Author: mzl
 * @CreateDate: 2020/10/22 10:14
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ServerResponse implements Serializable {

    public static final long serialVersionUID = 7498483649536881777L;

    private Integer status;
    private String msg;
    private Object data;

    public ServerResponse(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }



    /**
     * 以下的方法与构造方法的作用是一致的
     * @param
     * @return
     */
    /**
     * 操作成功调用的方法，用ServerResponse携带数据信息返回前端，与构造方法的作用是一致的
     * @return
     */
    public static ServerResponse success(){
        return new ServerResponse(ResponseCodeEnum.SUCCESS.getCode(), null, null);
    }

    public static ServerResponse success(String msg){
        return new ServerResponse(ResponseCodeEnum.SUCCESS.getCode(), msg, null);
    }

    public static ServerResponse success(Object data){
        return new ServerResponse(ResponseCodeEnum.SUCCESS.getCode(), null, data);
    }

    public static ServerResponse success(String msg, Object data){
        return new ServerResponse(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }

    /**
     * 操作失败调用的方法，用ServerResponse携带数据信息返回前端，与构造方法的作用是一致的
     * @return
     */
    public static ServerResponse error(){
        return new ServerResponse(ResponseCodeEnum.ERROR.getCode(), null, null);
    }

    public static ServerResponse error(String msg){
        return new ServerResponse(ResponseCodeEnum.ERROR.getCode(), msg, null);
    }

    public static ServerResponse error(Object data){
        return new ServerResponse(ResponseCodeEnum.ERROR.getCode(), null, data);
    }

    public static ServerResponse error(String msg, Object data){
        return new ServerResponse(ResponseCodeEnum.ERROR.getCode(), msg, data);
    }


}
