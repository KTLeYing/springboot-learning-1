package com.mzl.activeuserdemo1.service.impl;

import com.mzl.activeuserdemo1.dao.UserDao;
import com.mzl.activeuserdemo1.pojo.Mail;
import com.mzl.activeuserdemo1.pojo.User;
import com.mzl.activeuserdemo1.service.MailService;
import com.mzl.activeuserdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/11/27 21:22
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MailService mailService;

    /**
     *用户注册
     * @param user
     * @return
     */
    @Override
    public Boolean register(User user) {
        User user1 = userDao.save(user);
        if (user1 != null){
            //添加用户后就发邮件去激活
            //获取激活码
            String code = user1.getCode();
            System.out.println("code:" + code);
            Mail mail = new Mail();
            //user/checkCode?code=code(激活码)是我们点击邮件链接之后根据激活码查询用户，如果存在说明一致，将用户状态修改为“1”激活
            //上面的激活码发送到用户注册邮箱
            //注意:此处的链接地址,是项目内部地址,如果我们没有正式的服务器地址,暂时无法从qq邮箱中跳转到我们自己项目的激活页面
            mail.setContent("<a href=\"http://localhost:8080/user/checkCode?code="+code+"\">激活请点击:" + code + "</a>");
            mail.setSubject("来自--MZL网站的用户激活邮件");
            String[] tos = {user1.getEmail()};
            mail.setTos(tos);
            //发送邮件
            mailService.sendHtmlEmail(mail);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 校验邮箱中的code激活账户
     * @param code
     * @return
     */
    @Override
    public User checkCode(String code) {
        return userDao.findUserByCode(code);
    }

    /**
     * 更新用户激活状态
     * @param user
     */
    @Override
    public void updateUserStatus(User user) {
        userDao.save(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return userDao.findUserByUsernameAndPasswordAndStatus(user.getUsername(), user.getPassword(), 1);
    }


}
