package com.mzl.ehcachedemo1.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.mzl.ehcachedemo1.dao.UserDao;
import com.mzl.ehcachedemo1.entity.User;
import com.mzl.ehcachedemo1.service.UserService;
import com.sun.istack.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName :   UserServiceImpl
 * @Description: 用户业务逻辑实现类
 * @Author: mzl
 * @CreateDate: 2020/11/20 18:24
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * ####查询所有的用户(ehcache综合测试)#######
     * @return
     */
    @Override
    @Cacheable(value = "userCache", key = "'user.findAllUser'") //把方法返回值添加到Ehcache中做缓存,value是指定缓存策略(缓存)，key是缓存对象的名
    public List<User> findAllUser() {
        return userDao.findAll();
    }


    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    @Override
    public User findUserById(Long id) {
        return userDao.findById(id).get();
    }

    /**
     * 添加用户
     * @return
     */
    @Override
    @CacheEvict(value = "userCache", key = "'user.findAllUser'")  //清除缓存策略中以 user.findAllUser 为key 的缓存对象
    public String addUser(User user){
        User user1 = userDao.save(user);
        if (user1 != null){
            return "添加用户成功";
        }else {
            return "添加用户失败";
        }
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    @CacheEvict(value = "userCache", key = "'user.findAllUser'")  //清除缓存策略中以'user.findAllUser 为key 的缓存对象
    public String updateUser(User user) {
        Optional<User> user1 = userDao.findById(user.getId());
        System.out.println(user1);
        User user2 = user1.get();
        System.out.println(user2);
        //复制属性，把user得属性赋值给user2
        BeanUtil.copyProperties(user, user2, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true).setIgnoreProperties("age"));
        System.out.println(user2);
        User user3 = userDao.save(user2);
        if (user3 != null){
            return "修改用户成功";
        }else {
            return "修改用户失败";
        }
    }

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "userCache", key = "'user.findAllUser'")  //清除缓存策略中以'user.findAllUser 为key 的缓存对象
    public String deleteUser(Long id) {
        userDao.deleteById(id);
        return "删除用户成功";
    }

}
