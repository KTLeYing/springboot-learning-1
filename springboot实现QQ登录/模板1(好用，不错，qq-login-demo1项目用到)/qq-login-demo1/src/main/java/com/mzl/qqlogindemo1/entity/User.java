package com.mzl.qqlogindemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * QQ登录的用户实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String nickName;
    private String avatar;
    private String openid;

}
