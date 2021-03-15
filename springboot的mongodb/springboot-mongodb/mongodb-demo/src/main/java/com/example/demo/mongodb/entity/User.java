package com.example.demo.mongodb.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 路径：com.example.demo.mongodb.service.impl
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/9/30 17:19
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Data
public class User implements Serializable{

    // @id这个注解来对应mongo的_id这个字段
    @Id
    private String id;
    private String name;
    private int age;

}