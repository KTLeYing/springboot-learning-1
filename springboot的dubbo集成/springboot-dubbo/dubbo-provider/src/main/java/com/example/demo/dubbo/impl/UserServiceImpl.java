package com.example.demo.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.entity.User;
import com.example.demo.dubbo.mapper.UserMapper;
import com.example.demo.dubbo.service.UserServcie;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 路径：com.example.demo.dubbo.impl
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 17:30
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@Service(filter = "traceIdFilter")
public class UserServiceImpl implements UserServcie {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        log.info("getById  start id:{}",id);
        return userMapper.getById(id);
    }

    @Override
    public PageInfo<User> findUser(Integer pageNum, Integer pageSize) {
        log.info("findUser start pageNum：{}，pageSize：{}",pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.findUser();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        log.info("findUser end userPageInfo：{}", JSON.toJSON(userPageInfo));
        return userPageInfo;
    }

    @Override
    public int save(User user) {
        log.info("save start user:{}",JSON.toJSON(user));
        if(user.getId() != null && user.getId() != 0){
            log.info("save end");
            return userMapper.update(user);
        }else{
            log.info("save end");
            return userMapper.save(user);
        }
    }

    @Override
    public void delete(Integer id) {
        log.info("delete start id:{}",id);
        userMapper.delete(id);
        log.info("delete ned ");
    }
}
