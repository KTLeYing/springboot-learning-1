package com.mzl.shirojwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   RoleDTO
 * @Description: 角色dto
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:25
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {

    private Integer id;
    private String name;
    private short status;// 0:失效 1：生效',
    private Date createTime;
    private Date updateTime;
}
