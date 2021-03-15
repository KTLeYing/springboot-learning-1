package com.mzl.SSOdemo1.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResultMap
 * @Description: 返回的结果封装
 * @Author: mzl
 * @CreateDate: 2021/1/30 9:54
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultMap {

    private Integer code;
    private String msg;
    private Object data;

}
