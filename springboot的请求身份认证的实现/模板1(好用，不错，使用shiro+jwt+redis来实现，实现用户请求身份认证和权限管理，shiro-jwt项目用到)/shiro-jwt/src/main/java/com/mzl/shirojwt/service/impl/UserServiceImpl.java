package com.mzl.shirojwt.service.impl;

import com.mzl.shirojwt.Result.ResponseResult;
import com.mzl.shirojwt.constant.CommonConstant;
import com.mzl.shirojwt.dao.UserDao;
import com.mzl.shirojwt.entity.User;
import com.mzl.shirojwt.shiro.jwt.JwtToken;
import com.mzl.shirojwt.shiro.jwt.JwtUtil;
import com.mzl.shirojwt.service.UserService;
import com.mzl.shirojwt.util.MD5Util;
import com.mzl.shirojwt.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:27
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 用户登录
     * @param user
     * @param httpServletResponse
     * @return
     */
    @Override
    public ResponseResult login(User user, HttpServletResponse httpServletResponse) {
        // 对密码进行加密
        String password = MD5Util.GetMD5Code(user.getPassword());
        User user1 = new User();
        user1.setPassword(password);  //设置加密后的密码
        System.out.println("加密后的密码：" + password);
        user1.setAccount(user.getAccount());
        User user2 = userDao.login(user1);
        if (user2 == null){
            return new ResponseResult(0, "用户名或密码错误", false);
        }
        // 清除可能存在的Shiro权限信息缓存
        if(RedisUtil.hasKey(CommonConstant.PREFIX_SHIRO_CACHE + user.getAccount())){
            //删除之前登录的信息
            RedisUtil.delete(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + user.getAccount());
        }
        // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());
        RedisUtil.setEx(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + user.getAccount(), currentTimeMillis, Long.parseLong(refreshTokenExpireTime), TimeUnit.SECONDS);
        // 使用jwt进行登录，设置用户的jwt信息
        String token = JwtUtil.sign(user.getAccount(), currentTimeMillis);
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        httpServletResponse.setHeader("Authorization", token);
        httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
        return new ResponseResult(HttpStatus.OK.value(), "登录成功(Login Success.)", null);
    }

    /**
     * 查询用户列表
     * @return
     */
    @Override
    public ResponseResult userList() {
        List<User> userList = userDao.findAllUser();
        return new ResponseResult(HttpStatus.OK.value(), "查询成功", userList);
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {
        //进行密码加密
        String password =  MD5Util.GetMD5Code(user.getPassword());
        System.out.println("加密后的密码：" + password);
        //设置用户密码为加密后的密码
        user.setPassword(password);
        user.setStatus((short) 1);
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
        userDao.register(user);
    }

    /**
     * 查询用户自己的信息(角色的测试)
     * @return
     */
    @Override
    public ResponseResult userInfo(HttpServletRequest httpServletRequest) {
        User user1 = new User();
        String token = httpServletRequest.getHeader("Authorization");
        System.out.println("kkk...");
        System.out.println(token);
        user1.setAccount(JwtUtil.getClaim(token, CommonConstant.ACCOUNT));
        User user = userDao.selectOne(user1);
        return new ResponseResult(200, "查询成功", user);
    }


}
