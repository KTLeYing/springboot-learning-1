package com.mzl.rabbitmqdemo2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName :   Book
 * @Description: 书本实体类
 * @Author: mzl
 * @CreateDate: 2020/10/20 19:53
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book implements Serializable {

    public static final long serialVersionUID = -2164058270260403154L;

    private Integer id;
    private String name;

}
