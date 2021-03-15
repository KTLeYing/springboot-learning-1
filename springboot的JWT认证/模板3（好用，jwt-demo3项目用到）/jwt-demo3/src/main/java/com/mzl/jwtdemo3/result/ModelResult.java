package com.mzl.jwtdemo3.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ModelResult
 * @Description: 返回的数据封装对象
 * @Author: mzl
 * @CreateDate: 2020/10/10 15:55
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelResult<T> {

    private String code;  //状态码
    private String msg;   //信息
    private T data;    //数据


}
