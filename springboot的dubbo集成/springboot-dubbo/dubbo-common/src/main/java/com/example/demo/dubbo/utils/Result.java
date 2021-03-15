package com.example.demo.dubbo.utils;

import lombok.Data;

/**
 * 路径：com.example.demo.dubbo.utils
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/14 11:36
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Data
public class Result<T> {

    // 状态码
    private Integer code;
    // 提示信息
    private String mag;
    // 返回数据
    private Object data;
}
