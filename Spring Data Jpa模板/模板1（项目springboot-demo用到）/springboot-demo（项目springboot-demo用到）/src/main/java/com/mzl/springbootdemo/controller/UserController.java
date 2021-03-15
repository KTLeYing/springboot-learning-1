package com.mzl.springbootdemo.controller;

import com.mzl.springbootdemo.dao.UserRepository;
import com.mzl.springbootdemo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/23 15:25
 * @Version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    //注入jpa存储库
    @Autowired
    private UserRepository userRepository;

    //添加用户
    @PostMapping("/addUser")
    @ResponseBody
    public User addUser(@RequestParam String username, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println("user :" + user);
        userRepository.save(user);
        return user;
    }

    //更新用户
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(User user){
        userRepository.save(user);
        return "SUCCESS";
    }

    //删除用户(使用user来映射id)
    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUser(User user){
        userRepository.delete(user);
        return "SUCCESS";
    }

    //删除用户（用id）
    @RequestMapping("/deleteUserById")
    @ResponseBody
    public String deleteUserById(int uid){
        userRepository.deleteById(uid);
        return "SUCCESS";
    }

    //查找所有用户
    @RequestMapping("/findAllUser")
    @ResponseBody
    public Iterable<User> findAllUser(){
        return userRepository.findAll();
    }

    //查询用户（使用id）
    @RequestMapping("/findUserById")
    @ResponseBody
    public User findUserById(int uid){
        return userRepository.findById(uid).get();
    }

    //查询用户（使用iterable查询多个用户）
    @RequestMapping("/findUser")
    @ResponseBody
    public Iterable<User> findUser(String uids){
        System.out.println(uids);
        String[] arr = uids.split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String uid : arr) {
            arrayList.add(Integer.parseInt(uid));
        }
        Iterable<Integer> iterable = new ArrayList<>(arrayList);
        return userRepository.findAllById(iterable);
    }

    //自定义的jpa,使用用户名查询用户
    @RequestMapping("/findByName")
    @ResponseBody
    public User findByName(String username){
        return userRepository.findByUsername(username);
    }




    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        return "Hello World!";
    }


}
