package com.example.demo.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.entity.Admin;
import com.example.demo.dubbo.entity.User;
import com.example.demo.dubbo.mapper.AdminMapper;
import com.example.demo.dubbo.service.AdminService;
import com.example.demo.dubbo.service.UserServcie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 路径：com.example.demo.dubbo.impl
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/16 15:30
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@Service(filter = "traceIdFilter")
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Reference
    private UserServcie userServcie;

    @Override
    public Admin getById(Integer id) {
        log.info("getById  start id:{}", id);
        User user = userServcie.getById(id);
        log.info("getById  start user:{}", JSON.toJSON(user));
        if (user != null) {
            Admin admin = adminMapper.getById(id);
            log.info("getById  end admin:{}", JSON.toJSON(admin));
            return admin;
        }
        log.info("getById  start user为空！");
        return null;
    }
}
