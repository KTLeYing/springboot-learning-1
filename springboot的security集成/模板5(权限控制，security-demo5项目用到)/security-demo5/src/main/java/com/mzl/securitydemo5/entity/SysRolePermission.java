package com.mzl.securitydemo5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   SysRolePermission
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/14 17:36
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysRolePermission implements Serializable {

    private static final long serialVersionUID =  1L;

    private Integer roleId;
    private Integer permissionId;
}
