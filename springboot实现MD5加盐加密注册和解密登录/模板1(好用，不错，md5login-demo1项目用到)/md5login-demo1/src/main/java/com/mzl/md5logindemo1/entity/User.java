package com.mzl.md5logindemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/27 18:46
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public int id;//用户id
    public String username;//登录名
    public String password;//用户密码
    public String name;//姓名
    public Integer available; //是否已经激活

}
