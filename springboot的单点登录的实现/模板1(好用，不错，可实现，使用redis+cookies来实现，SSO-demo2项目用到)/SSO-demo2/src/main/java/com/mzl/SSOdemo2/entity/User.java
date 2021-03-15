package com.mzl.SSOdemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:29
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private String phone;

}
