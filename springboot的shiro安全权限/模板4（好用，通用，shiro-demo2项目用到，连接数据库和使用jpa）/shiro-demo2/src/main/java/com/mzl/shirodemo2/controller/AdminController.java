package com.mzl.shirodemo2.controller;

import com.mzl.shirodemo2.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   AdminController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/23 15:01
 * @Version: 1.0
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ResultMap resultMap;

    @RequestMapping("/getMessage")
    public ResultMap getMessage(){
        return resultMap.success().message("您拥有管理员权限，可以获得该接口的信息！");
    }

}
