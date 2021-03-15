package com.example.springdemo3.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName :   User
 * @Description: 用户实体
 * @Author: 21989
 * @CreateDate: 2020/7/23 0:14
 * @Version: 1.0
 */
@Entity   //应用于实体类，表明该实体类被JPA管理，将映射到指定的数据库表,所以实体类名要与表名一致
@Table(name = "user")  //这里User类对应的表是tb_user
@Data //使用lombok生成getter、setter
//@NoArgsConstructor //使用lombok生成无参构造方法
public class User {

    @Id  //应用于实体类的属性或者属性对应的getter方法，表示该属性映射为数据库表的主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //于@Id一同使用，表示主键的生成策略，通过strategy属性指定
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "phone")
    private String phone;
//    @Temporal(TemporalType.DATE)
//    private Date signDate;

    //springboot jpa 需要提供默认的构造函数
    public User() {  //代替@NaArgsConstructor注解
    }

    //此处省略了getter和setter方法


}
