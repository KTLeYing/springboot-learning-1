package com.mzl.exceldemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2021/2/4 1:37
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String username;
    private String password;
    private Integer status;

}
