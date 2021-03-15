package com.mzl.apidesigndemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   TokenInfo
 * @Description: token详细信息实体类（API和User都可以使用）
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:47
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfo {

    /** token类型: api:0  user:1 */
    private Integer tokenType;

    //APP信息
    private AppInfo appInfo;

    //用户信息只有User有）
    private UserInfo userInfo;

}
