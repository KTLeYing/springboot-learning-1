package com.example.mybatisplusdemo1.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo1.dto.UserDTO;
import com.example.mybatisplusdemo1.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/5 2:23
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 获取所有用户
     * @return
     */
    List<User> userList();

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    List<User> findUserByName(String username);

    /**
     * 模糊查找用户姓名含有li且年龄大于18且性别是男的，多条件复杂查询(使用mybatis-plus自带的方法)
     * @param sex
     * @param age
     * @return
     */
    List<User>  findUserByComplexCondition(String username, String sex, Integer age);

    /**
     * 插入保存用户
     * @param user
     * @return
     */
    int saveUser(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 通过用户主键id查找用户
     * @param id
     * @return
     */
    User findUserById(int id);

    /**
     * 删除用户(使用mybatis-plus自带的方法)
     * @param id
     * @return
     */
    int deleteUserById(Integer id);

    /**
     * 用户名删除用户(使用mybatis-plus自带的方法)
     * @param username
     * @return
     */
    int deleteUserByName(String username);

    /**
     * 用id批量删除用户(使用mybatis-plus自带的方法)
     * @param ids
     * @return
     */
    int deleteUsers(Integer[] ids);

    /**
     * 统计用户总数(有条件和无条件,使用mybatis-plus自带的方法)
     * @return
     */
    Integer userCount(Integer age);

    /**
     * 分页查询用户(无条件，使用mybatis-plus自带的分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<User> findUserByPage(Integer pageNum, Integer pageSize, Integer age);

    /**
     * 分页查询用户(有条件，使用mybatis-plus自带的分页插件)
     * @param pageNum
     * @param pageSize
     * @param age
     * @return
     */
    IPage<User> findUserByPage1(Integer pageNum, Integer pageSize, Integer age, String username);

    /**
     * 分页查询用户(无条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<User> findUserByPage2(Integer pageNum, Integer pageSize);

    /**
     * 获取所有用户(有条件/无条件，使用mybatis-plus自带的方法)
     * @return
     */
    List<User> findUserList();

    /**
     * 分页查询用户(有条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @param age
     * @param username
     * @return
     */
    PageInfo<User> findUserByPage3(Integer pageNum, Integer pageSize, Integer age, String username);

    /**
     * 查询直接返回一个用户dto
     * @return
     */
    List<UserDTO> findUserDTO();
}
