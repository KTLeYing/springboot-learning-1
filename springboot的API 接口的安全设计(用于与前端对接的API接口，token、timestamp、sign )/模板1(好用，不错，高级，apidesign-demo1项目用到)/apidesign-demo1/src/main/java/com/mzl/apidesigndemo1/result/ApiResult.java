package com.mzl.apidesigndemo1.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   ApiResult
 * @Description: api返回结果的封装实体类
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:58
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult {

    private String code;

    private String msg;

}
