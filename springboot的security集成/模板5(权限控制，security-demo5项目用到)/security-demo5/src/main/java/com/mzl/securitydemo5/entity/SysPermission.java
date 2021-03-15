package com.mzl.securitydemo5.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName :   SysPermission
 * @Description: 系统权限实体类
 * @Author: mzl
 * @CreateDate: 2020/11/14 14:01
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String url;
//    private Integer roleId;
    private String permission;

    //该字段将 permission 按逗号分割为了 list
    private List<String> permissions;

    //数组转换为list
    public List<String> getPermissions() {
        return Arrays.asList(this.permission.trim().split(","));
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
