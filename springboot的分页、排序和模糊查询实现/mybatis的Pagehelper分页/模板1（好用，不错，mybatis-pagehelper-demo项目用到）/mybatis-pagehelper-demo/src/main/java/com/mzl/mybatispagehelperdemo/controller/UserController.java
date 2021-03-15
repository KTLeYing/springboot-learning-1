package com.mzl.mybatispagehelperdemo.controller;

import com.github.pagehelper.PageInfo;
import com.mzl.mybatispagehelperdemo.entity.User;
import com.mzl.mybatispagehelperdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/11/6 20:35
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询用户
     * @return
     */
    @RequestMapping("findUserByPage")
    public PageInfo<User> findUserByPage(Integer pageNum, Integer pageSize){
        return userService.findUserByPage(pageNum, pageSize);
    }

    /**
     * 分页查询用户（有查询条件）
     * @return
     */
    @RequestMapping("findUserByPage1")
    public PageInfo<User> findUserByPage1(Integer pageNum, Integer pageSize, String username, Integer age){
        return userService.findUserByPage1(pageNum, pageSize, username, age);
    }

}
