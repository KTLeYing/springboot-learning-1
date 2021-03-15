package com.mzl.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import java.util.Date;

/**
 * @ClassName :   UserRole
 * @Description: 用户-角色实体类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:49
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Date createTime;

}
