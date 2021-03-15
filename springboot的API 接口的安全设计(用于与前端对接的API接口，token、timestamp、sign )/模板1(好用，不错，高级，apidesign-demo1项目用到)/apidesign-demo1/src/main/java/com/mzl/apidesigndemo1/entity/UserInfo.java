package com.mzl.apidesigndemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   UserInfo
 * @Description: 用户信息（只能用户使用）
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:49
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    //用户名
    private String username;

    //手机号
    private String mobile;

    //邮箱
    private String email;

    //密码
    private String password;

    //盐（MD5加密用到）
    private String salt;

    //用户可用的token
    private AccessToken accessToken;

    public UserInfo(String username, String password, String salt){
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

}
