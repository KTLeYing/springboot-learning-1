package com.example.mybatisplusdemo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   UserDTO
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/6 11:26
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String username1;
    private String password;
    private String sex;
    private Integer age1;
    private String phone1;
}
