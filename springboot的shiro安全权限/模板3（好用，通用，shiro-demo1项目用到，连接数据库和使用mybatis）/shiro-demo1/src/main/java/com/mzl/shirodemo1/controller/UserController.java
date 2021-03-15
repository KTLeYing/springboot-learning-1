package com.mzl.shirodemo1.controller;

import com.mzl.shirodemo1.entity.User;
import com.mzl.shirodemo1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/9/20 17:13
 * @Version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询所有用户
     * @param
     * @return
     */
    @RequestMapping("/listUsers")
    public List<User> listUsers(){
        return userService.listUsers(null);
    }

    /**
     * 用id来查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    public User getById(@PathVariable Integer id){
        return userService.getById(id);
    }

    /**
     *  添加用户
     * @param user
     * @return
     */
    @RequestMapping("/addUser")
    public Integer addUser(@RequestBody User user){
        System.out.println(user);
        return userService.addUser(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public Integer updateUser(@RequestBody User user){
         System.out.println(user);
         return userService.updateUser(user);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser/{id}")
    public Integer deleteUser(@PathVariable int id){
        return userService.deleteUser(id);
    }



}
