package com.mzl.shirodemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @ClassName :   Permission
 * @Description: 权限实体类
 * @Author: mzl
 * @CreateDate: 2020/9/20 15:25
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {
    private int id;
    private String name;
    private String url;
    private String description;

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
