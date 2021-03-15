package com.mzl.shirodemo2.controller;

import com.mzl.shirodemo2.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   GuestController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/9/23 14:56
 * @Version: 1.0
 */
@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    protected ResultMap resultMap;

    @RequestMapping("/enter")
    public ResultMap login(){
        return resultMap.success().message("欢迎进入，您的身份是游客");
    }

    @RequestMapping("/getMessage")
    public ResultMap submitLogin(){
        return resultMap.success().message("您拥有获得该接口的信息的权限！");
    }

}
