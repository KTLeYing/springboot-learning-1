package com.example.demo.dubbo.utils;

import lombok.Data;

/**
 * 路径：com.example.demo.dubbo.utils
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/14 11:38
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Data
public class ResultUtil {

    /**
     * 方法名：
     * 功能：成功返回方法
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/14 11:41
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static  Result success(Object obj){
        Result result = new Result();
        result.setCode(200);
        result.setMag("请求成功！");
        result.setData(obj);
        return result;
    }

    /**
     * 方法名：
     * 功能：失败返回方法
     * 描述：
     * 创建人：typ
     * 创建时间：2018/11/14 11:41
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    public static  Result error(Integer code,String mag){
        Result result = new Result();
        result.setCode(code);
        result.setMag(mag);
        result.setData(null);
        return result;
    }
}
