package com.mzl.redissentinel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   User
 * @Description: 用户
 * @Author: mzl
 * @CreateDate: 2021/1/25 10:49
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;
    private String value;
}
