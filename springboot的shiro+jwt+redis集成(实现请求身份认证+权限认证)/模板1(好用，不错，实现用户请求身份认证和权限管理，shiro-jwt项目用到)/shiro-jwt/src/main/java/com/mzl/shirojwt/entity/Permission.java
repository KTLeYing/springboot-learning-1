package com.mzl.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   Permission
 * @Description: 权限实体类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:48
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    private Integer id;
    private String title;
    private String action;
    private short status;// 0:失效 1：生效',
    private Date createTime;
    private Date updateTime;

}
