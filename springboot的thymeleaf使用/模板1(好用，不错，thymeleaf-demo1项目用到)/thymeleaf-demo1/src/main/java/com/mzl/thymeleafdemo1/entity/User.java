package com.mzl.thymeleafdemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/21 18:37
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private int id;
    private String name;
    private String sex;
    private int age;
    private String phone;

}
