package com.example.mybatisplusdemo1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2020/11/5 20:33
 * @Version: 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)    //type是主键的类型
    private Integer id;

    @TableField("username")    //如果不写@TableField注解，属性会和相应的数据库表字段同名映射
    private String username;

    @TableField("password")
    private String password;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private Integer age;

    @TableField("phone")
    private String phone;

}
