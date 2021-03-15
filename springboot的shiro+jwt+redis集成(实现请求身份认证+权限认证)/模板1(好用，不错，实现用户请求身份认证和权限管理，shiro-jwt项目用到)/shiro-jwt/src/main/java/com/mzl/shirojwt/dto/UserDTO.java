package com.mzl.shirojwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName :   UserDTO
 * @Description: 用户dto
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:25
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;
    private String account;
    private String password;
    private short status;
    private Date createTime;
    private Date updateTime;
}
