package com.example.demo.mongodb.service;

import com.example.demo.mongodb.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 路径：com.example.demo.mongodb.service
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/9/30 17:16
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
public interface UserService extends MongoRepository<User, String> {

    User findByName(String name);

}
