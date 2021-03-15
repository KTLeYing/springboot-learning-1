package com.mzl.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   RolePermission
 * @Description: 角色-权限实体类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:49
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {

    private Integer roleId;
    private Integer permissionId;
    private Date createTime;

}
