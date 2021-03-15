package com.mzl.shirojwtSSO.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   Role
 * @Description: 角色实体类
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:54
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer id;
    private String name;

}
