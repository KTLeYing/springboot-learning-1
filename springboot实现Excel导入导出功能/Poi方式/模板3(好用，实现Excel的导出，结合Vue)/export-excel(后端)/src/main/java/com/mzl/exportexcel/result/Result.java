package com.mzl.exportexcel.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: 返回结果
 * @Author: mzl
 * @CreateDate: 2021/1/4 10:43
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private boolean flag;
    private int code;
    private String msg;
    private T data;


}
