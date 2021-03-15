package com.mzl.springbootdemo1.service.impl;

import com.mzl.springbootdemo1.entity.User;
import com.mzl.springbootdemo1.repository.UserRepository;
import com.mzl.springbootdemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   UserServiceImpl
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:27
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    //注入依赖，jpa存储库
    @Autowired
    private UserRepository userRepository;

    //查询所有用户
    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    //添加用户
    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    //修改用户
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    //查询用户信息（用id）
    @Override
    public User findUserById(int uid) {
        return userRepository.findById(uid).get();
    }

    //查询用户（用户id查询多个用户）
    @Override
    public Iterable<User> findUser(String uids) {
        String[] arr = uids.split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String uid : arr) {
            System.out.println(uid);
            arrayList.add(Integer.valueOf(uid));
        }
        Iterable<Integer> iterable = new ArrayList<>(arrayList);
        return userRepository.findAllById(iterable);
    }

    //删除用户(用id)
    @Override
    public void deleteUserById(int uid) {
        userRepository.deleteById(uid);
    }

    //删除用户（用user来映射id）
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
