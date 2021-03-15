package com.mzl.mybatispagehelperdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/6 20:37
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String sex;
    private Integer age;
    private String phone;
}
