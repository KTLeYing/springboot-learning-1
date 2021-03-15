package com.mzl.SSOdemo2.dao;

import com.mzl.SSOdemo2.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/2/8 0:30
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    /**
     * 添加用户
     * @param user
     * @return
     */
    @Insert("insert into user(username, password, phone) values(#{username}, #{password}, #{phone})")
    int addUser(User user);
}
