package com.fsl.dubboproducer.service.impl;

import com.fsl.dubboapi.service.UserService;
import com.fsl.dubboapi.service.dto.User;

import java.util.Date;

@com.alibaba.dubbo.config.annotation.Service(version = "${demo.service.version}")
public class UserServiceImpl implements UserService {

    @Override
    public User getUser() {
        User user = new User();
        user.setId(10001L);
        user.setAge(18);
        user.setBirthday(new Date());
        user.setName("king james");
        user.setAddress("广东省广州市花都区");
        return user;
    }
}
