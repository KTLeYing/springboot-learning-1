package com.example.demo.dubbo.service;

import com.example.demo.dubbo.entity.User;
import com.github.pagehelper.PageInfo;

/**
 * 路径：com.example.demo.dubbo.service
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 17:25
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
public interface UserServcie {

    User getById(Integer id);

    PageInfo<User> findUser(Integer pageNum, Integer pageSize);

    int save(User user);

    void delete(Integer id);
}
