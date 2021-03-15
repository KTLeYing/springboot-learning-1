package com.mzl.md5logindemo1.service.impl;

import com.mzl.md5logindemo1.common.Result;
import com.mzl.md5logindemo1.dao.UserDao;
import com.mzl.md5logindemo1.entity.User;
import com.mzl.md5logindemo1.service.UserService;
import com.mzl.md5logindemo1.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/11/27 19:03
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户登录验证
     * @param map
     * @param response
     * @return
     */
    @Override
    public Result login(Map<String, String> map, HttpServletRequest request, HttpServletResponse response) {
        try{
            String username = map.get("username");
            String password = map.get("password");

            //先进行密码的加密，再去查询
            String pd = MD5Utils.encode(username, password);
            //从数据库中查询该用户
            User user = userDao.findUser(username, pd);
            if (user == null){
                return new Result(false, 500, "用户名或密码错误");
            }else if (user.getAvailable() == 0){
                return new Result(false, 500, "您的账户未激活，请登录邮箱激活");
            }

            //登录成功
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return new Result(true, 200, "登录成功", "session为" + session.getAttribute("user"));

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, 500, "服务器错误，登录失败");
        }
    }

    /**
     * 用户注册
     * @param map
     * @return
     */
    @Override
    public Result register(Map<String, Object> map) {
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String name = (String) map.get("name");

        //查询用户名是否已经存在
        String username1 = userDao.existUser(username);

        if (!StringUtils.isEmpty(username1)){   //用户名为空
            return new Result(false, 500, "此用户名已存在，注册失败");
        }

        //先对密码进行加密
        String pd = MD5Utils.encode(username, password);

        //添加用户到数据库
        int num = userDao.register(username, pd, name);
        if (num > 0){
            return new Result(true, 200, "注册成功");
        }else {
            return new Result(false, 500, "注册失败");
        }
    }

    /**
     * 用户激活
     * @param username
     * @return
     */
    @Override
    public Result active(String username) {
        int num = userDao.active(username);
        if (num > 0){
            return new Result(true, 200, "激活成功");
        }else {
            return new Result(false, 500, "激活失败");
        }
    }


}
