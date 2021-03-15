package com.mzl.springbootstudentsystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @ClassName :   Student
 * @Description: 学生实体类
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:20
 * @Version: 1.0
 */
@Entity
@Data
@NoArgsConstructor
public class Student {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String account;
    private Integer age;
    private String name;
    private String password;

}
