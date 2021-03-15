package com.example.demo.dubbo.mapper;

import com.example.demo.dubbo.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 路径：com.example.demo.dubbo.mapper
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 17:32
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Mapper
public interface UserMapper {

    User getById(Integer id);

    List<User> findUser();

    int save(User user);

    int update(User user);

    void delete(Integer id);
}
