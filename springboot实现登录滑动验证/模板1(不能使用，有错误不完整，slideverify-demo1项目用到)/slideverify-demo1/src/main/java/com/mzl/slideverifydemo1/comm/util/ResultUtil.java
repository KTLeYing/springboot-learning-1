package com.mzl.slideverifydemo1.comm.util;

import com.mzl.slideverifydemo1.comm.entity.ReturnT;
import com.mzl.slideverifydemo1.comm.enums.ResultEnum;

public class ResultUtil {

    public static ReturnT success(Object object){
        ReturnT result = new ReturnT(ResultEnum.SUCCESS);
        result.setData(object);
        return  result;
    }

    public static ReturnT successNoData(){
        return  success(null);
    }
    public static ReturnT error(Integer code,String msg){
        ReturnT result = new ReturnT();
        result.setCode(code);
        result.setMsg(msg);
        return  result;
    }

    public static ReturnT normal(ResultEnum resultEnum){
        ReturnT result = new ReturnT();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return  result;
    }

}
