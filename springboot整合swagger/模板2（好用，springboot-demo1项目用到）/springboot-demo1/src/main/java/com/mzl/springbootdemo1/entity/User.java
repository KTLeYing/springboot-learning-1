package com.mzl.springbootdemo1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:24
 * @Version: 1.0
 */
@Entity
@Data  //getter、setter方法
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int uid;
    private String username;
    private String password;

}
