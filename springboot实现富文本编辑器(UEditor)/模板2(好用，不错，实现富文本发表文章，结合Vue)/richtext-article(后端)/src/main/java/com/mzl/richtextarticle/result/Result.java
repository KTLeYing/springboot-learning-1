package com.mzl.richtextarticle.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Result
 * @Description: 结果返回封装
 * @Author: mzl
 * @CreateDate: 2021/1/6 10:18
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    private int code;
    private String msg;
    private T data;

}
