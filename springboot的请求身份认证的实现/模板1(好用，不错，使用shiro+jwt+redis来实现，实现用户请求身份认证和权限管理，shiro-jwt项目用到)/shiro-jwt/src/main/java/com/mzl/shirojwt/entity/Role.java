package com.mzl.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   Role
 * @Description: 角色实体类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:48
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Integer id;
    private String name;
    private short status;
    private Date createTime;
    private Date updateTime;

}
