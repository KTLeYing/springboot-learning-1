package com.mzl.redisuserlike.controller;

import com.mzl.redisuserlike.entity.UserInfo;
import com.mzl.redisuserlike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/12/16 10:33
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有的用户信息（包括每个用户对应的点赞的）
     * @return
     */
    @RequestMapping("/findAllUser")
    public List<UserInfo> findAllUser(){
        return userService.findAll();
    }

}
