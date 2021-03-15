package com.mzl.shirojwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   PermissionDTO
 * @Description: 权限dto
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:25
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {

    private Integer id;
    private String title;
    private String action;
    private short status;// 0:失效 1：生效',
    private Date createTime;
    private Date updateTime;
}
