package com.mzl.shirodemo3.controller;

import com.mzl.shirodemo3.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :   AuthcController
 * @Description: 验证控制器，对各种皆苦进行验证
 * @Author: mzl
 * @CreateDate: 2020/9/24 19:58
 * @Version: 1.0
 */
@RestController
@RequestMapping("/authc")
public class AuthcController {

    /**
     *用户首页
     * @return
     */
    @RequestMapping("/index")
    public Object index(){
        //获取安管理工具类的主体对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("user");
        //把user对象转换为字符串
        return user.toString();
    }

    /**
     * 管理员首页
     * @return
     */
    @RequestMapping("/admin")
    public Object admin(){
        return "欢迎来到管理员页面";
    }

    /**
     * 删除移除操作
     * @return
     */
    @RequestMapping("/removable")
    public Object removable(){
        return "删除成功...";
    }

    /**
     * 创建或更新操作
     * @return
     */
    @RequestMapping("/renewable")
    public Object renewable(){
        return "更新或创建成功...";
    }

    /**
     * 查询操作（可检索操作）
     * @return
     */
    @RequestMapping("/retrievable")
    public Object retrievable(){
        return "查询操作成功...";
    }

}
