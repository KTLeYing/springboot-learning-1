package com.mzl.SSOdemo2.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResultMap
 * @Description: 返回的结果封装
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:31
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap {

    private Integer code;
    private String msg;
    private Object data;

}
