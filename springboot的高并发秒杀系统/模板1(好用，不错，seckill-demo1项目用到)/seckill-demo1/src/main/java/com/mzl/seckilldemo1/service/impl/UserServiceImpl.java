package com.mzl.seckilldemo1.service.impl;

import com.mzl.seckilldemo1.dao.UserDao;
import com.mzl.seckilldemo1.dto.LoginDTO;
import com.mzl.seckilldemo1.dto.UserDTO;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.exception.GlobalException;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.redis.redis.UserKey;
import com.mzl.seckilldemo1.result.ResultCode;
import com.mzl.seckilldemo1.service.UserService;
import com.mzl.seckilldemo1.util.MD5Util;
import com.mzl.seckilldemo1.util.UUIDUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2021/3/1 17:49
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserDao userDao;

    /**
     * 通过token获取用户信息
     * @param response
     * @param token
     * @return
     */
    @Override
    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        //从token中获取用户的信息
        User user = redisService.get(UserKey.token, token, User.class);
        //延长有效期，有效期等于最后一次操作+有效期
        if (user != null){
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     * 用户登录
     * @param response
     * @param loginDTO
     * @return
     */
    @Override
    public String login(HttpServletResponse response, LoginDTO loginDTO) {
        if (loginDTO == null){
            throw new GlobalException(ResultCode.PASSWORD_ERROR.getCode(), ResultCode.PASSWORD_ERROR.getMsg());
        }
        String mobile= loginDTO.getMobile();
        String formPass = loginDTO.getPassword();
        //判断手机号是否存在
        User user = getById(Long.parseLong(mobile));
        if (user == null){
            throw new GlobalException(ResultCode.MOBILE_NOT_EXIST.getCode(), ResultCode.MOBILE_NOT_EXIST.getMsg());
        }
        System.out.println("hhh");
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)){
            throw new GlobalException(ResultCode.PASSWORD_ERROR.getCode(), ResultCode.PASSWORD_ERROR.getMsg());
        }
        System.out.println("hhh");
        //生成唯一id作为token
        String token = UUIDUtil.uuid();
        System.out.println(token);
        addCookie(response, token, user);
        return token;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public int register(UserDTO user) {
        user.setSalt("1a2b3c4d");
        user.setLoginCount(1);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setRegisterDate(now);
        user.setLastLoginDate(now);
        user.setPassword(MD5Util.inputPassToDbPass(user.getPassword(), user.getSalt()));
        return userDao.register(user);
    }

    /**
     * 将token做为key，用户信息做为value 存入redis模拟session
     * 同时将token存入cookie，保存登录状态
     */
    public void addCookie(HttpServletResponse response, String token, User user){
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");//设置为网站根目录
        response.addCookie(cookie);
    }

    /**
     * 判断手机号是否存在
     * @param
     * @return
     */
    public User getById(long id) {
        //对象缓存到redis
        User user = redisService.get(UserKey.getById, "" + id, User.class);
        if (user != null){
            return user;
        }
        //redis缓存为空，从数据库中获取用户
        user = userDao.getById(id);
        //再存入缓存
        if (user != null){
            redisService.set(UserKey.getById, "" + id, user);
        }
        return user;
    }






}
