package com.mzl.shirojwtSSO.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:53
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private Integer ban;

}
