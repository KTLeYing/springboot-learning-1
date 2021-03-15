package com.fsl.dubboapi.service.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    private Long id;

    private String name;

    private String address;

    private Integer age;

    private Date birthday;

}
