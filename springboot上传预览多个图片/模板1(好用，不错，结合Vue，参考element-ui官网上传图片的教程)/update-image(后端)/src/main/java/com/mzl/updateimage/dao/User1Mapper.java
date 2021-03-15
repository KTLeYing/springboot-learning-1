package com.mzl.updateimage.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.mzl.updateimage.entity.User1;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface User1Mapper {

    /**
     * 更新用户头像
     * @param user
     * @return
     */
    @Update("update user1 set image_path = #{imagePath} where username = #{username}")
    int updateUser(User1 user);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Select("select * from user1 where username = #{username} and password = #{password}")
    User1 login(String username, String password);
}