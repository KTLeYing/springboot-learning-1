package com.mzl.alteruserimage1.service.impl;

import com.mzl.alteruserimage1.dao.UserDao;
import com.mzl.alteruserimage1.entity.User;
import com.mzl.alteruserimage1.service.UserService;
import com.mzl.alteruserimage1.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName :   UserServiceImpl
 * @Description:用户业务逻辑接口实现类
 * @Author: mzl
 * @CreateDate: 2021/2/19 13:52
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ImageUtil imageUtil;

    /**
     * 上传文件处理
     * @param file
     * @return
     */
    @Override
    public String uploadImage(MultipartFile file, User user) {
        try{
            String path = imageUtil.upload(file);
            //保存用户信息到数据库
            user.setImagePath(path);
            userDao.saveUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 查询用户
     * @param id
     * @return
     */
    @Override
    public User findUserById(int id) {
        return userDao.findUserById(id);
    }


}
