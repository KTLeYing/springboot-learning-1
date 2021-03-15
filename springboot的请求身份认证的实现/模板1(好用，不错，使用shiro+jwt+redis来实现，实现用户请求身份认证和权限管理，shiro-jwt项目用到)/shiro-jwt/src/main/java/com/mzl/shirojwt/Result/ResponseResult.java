package com.mzl.shirojwt.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResponseResult
 * @Description: 返回响应的结果集封装
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:36
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseResult {

    //状态码
    private Integer code;
    //返回的信息
    private String msg;
    //返回的数据
    private Object data;

}
