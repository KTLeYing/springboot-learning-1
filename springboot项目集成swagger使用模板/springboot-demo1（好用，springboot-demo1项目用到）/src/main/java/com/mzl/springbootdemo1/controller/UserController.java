package com.mzl.springbootdemo1.controller;

import com.mzl.springbootdemo1.entity.User;
import com.mzl.springbootdemo1.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName :   UserController
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:26
 * @Version: 1.0
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    /*
    * 其中@ApiOperation是接口的描述信息，@ApiImplicitParam针对具体的参数添加描述，@ApiImplicitParams则是多个@ApiImplicitParam，
    * 更多注解使用，可以参考官方文档。需要注意的是，使用@PathVariable需要设置paramType="path"，而使用@RequestParam时，
    * 需要设置paramType="query"，不然可能会导致好好的接口无法接受到请求参数。当没有@PathVariable和@RequestParam，默认paramType="query",
    * @RequestMapping自动根据参数变量名或者实体类的属性名来匹配映射赋值（像一般的一样）
    * */

    //注入依赖
    @Autowired
    private UserService userService;

    //添加用户
    @ApiOperation("/添加用户")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)
    })
    public User addUser(User user){
        System.out.println(user);
        userService.addUser(user);
        return user;
    }

    //修改用户
    @ApiOperation("/修改用户")
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "int", required = true),
            @ApiImplicitParam(name = "username", value = "用户名", dataType = "String", required = true),
            @ApiImplicitParam(name = "password", value = "密码", dataType = "String", required = true)
    })
    public String updateUser(User user){
        System.out.println(user);
        userService.updateUser(user);
        return "SUCCESS";
    }

    //删除用户(用id)
    @ApiOperation("用id删除用户")
    @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "int", required = true)
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.GET)
    public String  deleteUserById(int uid){
        userService.deleteUserById(uid);
        return "SUCCESS";
    }

    //删除用户（用user来映射id）
    @ApiOperation("用user映射删除用户")
    @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "int", required = true)
    @RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
    public String deleteUser(User user){
        userService.deleteUser(user);
        return "SUCCESS";
    }

    //查询所有用户
    @ApiOperation("查询所有用户")
    @RequestMapping(value = "/findAllUser", method = RequestMethod.GET)
    public List<User> findAllUser(){
        return userService.findAllUser();
    }

    //查询用户信息（用id）
    @ApiOperation("用id查询用户信息")
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    @ApiImplicitParam(name = "uid", value = "用户Id", dataType = "int", required = true)
    public User findUserById(int uid){
        return userService.findUserById(uid);
    }

    //查询用户（用户id查询多个用户）
    @ApiOperation("用户id查询多个用户")
    @RequestMapping(value = "/findUser", method = RequestMethod.GET)
    @ApiImplicitParam(name = "uids", value = "用户Ids", dataType = "String", required = true)
    public Iterable<User> findUser(String uids){
        System.out.println(uids);
        return userService.findUser(uids);
    }




}
