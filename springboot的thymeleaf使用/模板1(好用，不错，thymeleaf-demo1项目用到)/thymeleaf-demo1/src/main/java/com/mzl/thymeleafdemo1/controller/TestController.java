package com.mzl.thymeleafdemo1.controller;

import com.mzl.thymeleafdemo1.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName :   controller
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/21 18:17
 * @Version: 1.0
 */
@Controller
public class TestController {

    /**
     * thymeleaf测试
     * @return
     */
    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }

    /**
     * 测试1，显示实体类,使用ModelAndView来封装请求路径和数据给前端spring web来解析利用
     * @param modelAndView
     * @return
     */
    @RequestMapping("test1")
    public ModelAndView test1(ModelAndView modelAndView){
        //设置跳转的视图，默认是在/template/目录下的文件
        modelAndView.setViewName("index");
        modelAndView.addObject("title", "我的thymeleaf测试");
        modelAndView.addObject("desc", "欢迎进入该系统");
        User user = new User();
        user.setId(0);
        user.setName("马振乐");
        user.setSex("男");
        user.setAge(18);
        user.setPhone("13652707142");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    /**
     * 测试1，显示实体类,使用HttpServletRequest来封装数据给前端来解析利用,直接通过普通的字符串返回目标视图（Springing web会解析路径和数据）
     * @param
     * @return
     */
    @RequestMapping("test2")
    public String test2(HttpServletRequest request){
        //设置跳转的视图，默认是在/template/目录下的文件
        request.setAttribute("title", "我的thymeleaf测试");
        request.setAttribute("desc", "欢迎进入该系统");
        User user = new User();
        user.setId(0);
        user.setName("马振乐");
        user.setSex("男");
        user.setAge(18);
        user.setPhone("13652707142");
        request.setAttribute("user", user);
        return "index";
    }


}
