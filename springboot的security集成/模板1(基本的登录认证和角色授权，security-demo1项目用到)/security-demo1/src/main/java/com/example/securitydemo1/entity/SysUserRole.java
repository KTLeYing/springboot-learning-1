package com.example.securitydemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   SysUserRole
 * @Description: 用户-角色实体类
 * @Author: mzl
 * @CreateDate: 2020/11/12 20:53
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserRole implements Serializable {

    public static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer roleId;

}
