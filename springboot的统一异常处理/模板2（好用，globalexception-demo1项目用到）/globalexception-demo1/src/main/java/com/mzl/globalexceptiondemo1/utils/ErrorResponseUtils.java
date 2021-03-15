package com.mzl.globalexceptiondemo1.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResponseUtils
 * @Description: 返回响应错误信息数据模板
 * @Author: mzl
 * @CreateDate: 2020/10/11 20:39
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseUtils {

    private int code;
    private String message;

}
