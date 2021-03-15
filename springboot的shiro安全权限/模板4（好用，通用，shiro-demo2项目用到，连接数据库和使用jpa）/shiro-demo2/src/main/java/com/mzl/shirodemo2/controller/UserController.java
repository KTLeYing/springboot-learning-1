package com.mzl.shirodemo2.controller;

import com.mzl.shirodemo2.service.UserService;
import com.mzl.shirodemo2.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/9/21 21:48
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ResultMap resultMap;

    @RequestMapping("/getMessage")
    public ResultMap getMessage(){
        return resultMap.success().message("您拥有用户权限，可以获得该接口的信息！");
    }


}
