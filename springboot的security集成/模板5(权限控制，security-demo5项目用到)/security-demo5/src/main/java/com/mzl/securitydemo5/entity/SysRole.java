package com.mzl.securitydemo5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   SysRole
 * @Description: 角色实体类
 * @Author: mzl
 * @CreateDate: 2020/11/12 20:52
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
}
