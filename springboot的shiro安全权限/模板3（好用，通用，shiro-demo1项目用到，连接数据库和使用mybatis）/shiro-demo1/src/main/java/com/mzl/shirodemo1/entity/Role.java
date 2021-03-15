package com.mzl.shirodemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName :   Role
 * @Description: 角色实体类
 * @Author: mzl
 * @CreateDate: 2020/9/20 15:24
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    private int id;
    private String role;
    private Integer roleLevel;
    private String description;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", roleLevel=" + roleLevel +
                ", description='" + description + '\'' +
                '}';
    }
}
