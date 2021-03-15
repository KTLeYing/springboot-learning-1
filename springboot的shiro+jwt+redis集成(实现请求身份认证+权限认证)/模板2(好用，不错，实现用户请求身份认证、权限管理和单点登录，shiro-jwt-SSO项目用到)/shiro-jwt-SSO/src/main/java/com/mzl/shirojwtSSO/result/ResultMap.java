package com.mzl.shirojwtSSO.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResultMap
 * @Description: 结果集封装
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:34
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
