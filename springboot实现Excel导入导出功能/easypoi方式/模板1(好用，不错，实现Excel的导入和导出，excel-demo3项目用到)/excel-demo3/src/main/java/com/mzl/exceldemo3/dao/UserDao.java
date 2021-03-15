package com.mzl.exceldemo3.dao;

import com.mzl.exceldemo3.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @InterfaceName :   UserDao
 * @Description: 用户dao
 * @Author: mzl
 * @CreateDate: 2021/2/3 18:27
 * @Version: 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 保存表格（用户）的数据到数据库
     * @param user
     */
    @Insert("insert into user(username, password, status) values(#{username}, #{password}, #{status})")
    void addUser(User user);

    /**
     * 查询所有的用户
     * @return
     */
    @Select("select * from user")
    List<User> findAll();
}
