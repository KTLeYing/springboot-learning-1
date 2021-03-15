package com.mzl.slideverifydemo1.modules.sys.service;

import com.mzl.slideverifydemo1.comm.service.CrudService;
import com.mzl.slideverifydemo1.modules.sys.dao.UserDao;
import com.mzl.slideverifydemo1.modules.sys.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends CrudService<UserDao,User> {
    public List<User> getUserByLoginName(User user) {
        return dao.getUserByLoginName(user);
    }
}