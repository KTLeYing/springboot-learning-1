package com.mzl.avoidpostdemo2.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ResultMap
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap<T> {

    private Integer code;
    private String msg;
    private T data;

    public ResultMap success(){
        return new ResultMap(101, "操作成功", null);
    }

    public ResultMap error(){
        return new ResultMap(102, "操作失败", null);
    }

}
