package com.example.mybatisplusdemo1.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo1.dto.UserDTO;
import com.example.mybatisplusdemo1.entity.User;
import com.example.mybatisplusdemo1.service.UserService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xerces.internal.util.EntityResolverWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   UserController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/5 2:16
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取所有用户(普通的mybatis自定义的方法)
     * @return
     */
    @RequestMapping("/userList")
    public List<User> userList(){
        return userService.userList();
    }

    /**
     * 获取所有用户(有条件/无条件，使用mybatis-plus自带的方法)
     * @return
     */
    @RequestMapping("/findUserList")
    public List<User> findUserList(){
        return userService.findUserList();
    }

    /**
     * 通过用户主键id查找用户(使用mybatis-plus自带的方法)
     * @param
     * @return
     */
    @RequestMapping("/findUserById")
    public User findUserById(int id) {
        //使用mybatis-plus自带的方法，返回值是list
        return userService.findUserById(id);
    }

    /**
     * 通过用户名查找用户(使用mybatis-plus自带的方法)
     * @param username
     * @return
     */
    @RequestMapping("findUserByName")  //方法的请求路径前可以不加/，因为配置文件配置好servlet-path配置了
    public List<User> findUserByName(String username){
       return userService.findUserByName(username);
    }

    /**
     * 模糊查找用户姓名含有li且年龄大于18且性别是男的，多条件复杂查询(使用mybatis-plus自带的方法)
     * @param sex
     * @return
     */
    @RequestMapping("findUserByComplexCondition")  //方法的请求路径前可以不加/，因为配置文件配置好servlet-path配置了
    public List<User> findUserByComplexCondition(String username, String sex, Integer age) {
        //经常查询条件包装){
        return userService. findUserByComplexCondition(username, sex, age);
    }

    /**
     * 插入保存用户(使用mybatis-plus自带的方法)
     * @param user
     * @return
     */
    @RequestMapping("saveUser")
    public String saveUser(User user){
        Integer num = userService.saveUser(user);
        if (num > 0){
            return "新增用户成功";
        }else {
            return "新增用户失败";
        }
    }

    /**
     * 修改用户(使用mybatis-plus自带的方法)
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    public String updateUser(User user){
        int num = userService.updateUser(user);
        if (num > 0){
            return "修改用户成功";
        }else {
            return "修改用户失败";
        }
    }

    /**
     * 通过id删除用户(使用mybatis-plus自带的方法)
     * @param id
     * @return
     */
    @RequestMapping("deleteUserById")
    public String deleteUserById(Integer id){
        int num = userService.deleteUserById(id);
        if (num > 0){
            return "删除用户成功";
        }else {
            return "删除用户失败";
        }
    }

    /**
     * 用户名删除用户(使用mybatis-plus自带的方法)
     * @param username
     * @return
     */
    @RequestMapping("deleteUserByName")
    public String deleteUserByName(String username){
        int num = userService.deleteUserByName(username);
        if (num > 0){
            return "删除用户成功";
        }else {
            return "删除用户失败";
        }
    }

    /**
     * 用id批量删除用户(使用mybatis-plus自带的方法)
     * 前端的接口测试工具对于数组参数的设置直接用逗号隔开多个值就行
     * @param ids
     * @return
     */
    @RequestMapping("deleteUsers")
    public String deleteUsers(Integer[] ids){
        int num = userService.deleteUsers(ids);
        if (num > 0){
            return "批量删除用户成功";
        }else {
            return "批量删除用户失败";
        }
    }

    /**
     * 统计用户总数(有条件和无条件,使用mybatis-plus自带的方法)
     * @return
     */
    @RequestMapping("userCount")
    public Integer userCount(Integer age){
        return userService.userCount(age);
    }

    /**
     * 分页查询用户(无条件，使用mybatis-plus自带的分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("findUserByPage")
    public IPage<User> findUserByPage(Integer pageNum, Integer pageSize, Integer age){
        return userService.findUserByPage(pageNum, pageSize, age);
    }

    /**
     * 分页查询用户(有条件，使用mybatis-plus自带的分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("findUserByPage1")
    public IPage<User> findUserByPage1(Integer pageNum, Integer pageSize, Integer age, String username){
        return userService.findUserByPage1(pageNum, pageSize, age, username);
    }

    /**
     * 分页查询用户(无条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("findUserByPage2")
    public PageInfo<User> findUserByPage2(Integer pageNum, Integer pageSize){
        return userService.findUserByPage2(pageNum, pageSize);
    }

    /**
     * 分页查询用户(有条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("findUserByPage3")
    public PageInfo<User> findUserByPage3(Integer pageNum, Integer pageSize, Integer age, String username){
        return userService.findUserByPage3(pageNum, pageSize, age, username);
    }

    /**
     * 查询直接返回一个用户dto
     * @return
     */
    @RequestMapping("findUserDTO")
    public List<UserDTO> findUserDTO(){
        return userService.findUserDTO();
    }

}
