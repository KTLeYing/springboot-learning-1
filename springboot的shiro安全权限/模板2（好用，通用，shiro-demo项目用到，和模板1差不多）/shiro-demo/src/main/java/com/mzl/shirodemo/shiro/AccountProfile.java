package com.mzl.shirodemo.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName :   AccountProfile
 * @Description: 用户对象的基本资料信息(序列化对象),相当于实体类那种，Subject进行login操作，参数是封装了用户信息的token
 * @Author: mzl
 * @CreateDate: 2020/9/16 19:50
 * @Version: 1.0
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;
    private String username;;
    private String gender;
    private String sign;

}
