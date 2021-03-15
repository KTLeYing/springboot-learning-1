package com.example.demo.mongodb.controller;

import com.example.demo.mongodb.entity.User;
import com.example.demo.mongodb.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 路径：com.example.demo.mongodb.controller
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/9/30 17:19
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/find")
    public List<User> find(){
        List<User> list = userService.findAll();
        return list;
    }

    @GetMapping("/getById")
    public Optional<User> getById(String id){

        return userService.findById(id);
    }

    @GetMapping("/getByName")
    public User getByName(String name){
        User user = userService.findByName(name);
        return user;
    }

    @PostMapping("/save")
    public String save(@RequestBody User user){
        try{
            userService.save(user);
            return "成功！";
        }catch (Exception e){
            return "失败！";
        }
    }

    @GetMapping("/del")
    public String del(String id){
        try{
            userService.deleteById(id);
            return "成功！";
        }catch (Exception e){
            return "失败！";
        }
    }

}