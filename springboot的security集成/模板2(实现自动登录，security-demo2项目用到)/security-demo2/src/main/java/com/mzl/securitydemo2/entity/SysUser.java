package com.mzl.securitydemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   SysUser
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2020/11/12 20:52
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String password;

}
