package com.mzl.jwtdemo3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.management.StandardEmitterMBean;

/**
 * @ClassName :   UserLoginDto
 * @Description: 前端和后端之间数据传输对象
 * @Author: mzl
 * @CreateDate: 2020/10/11 13:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
public class UserLoginDto {

    private String username;
    private String password;

}
