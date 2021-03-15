package com.mzl.slideverifydemo1.modules.sys.dao;

import com.mzl.slideverifydemo1.comm.dao.CrudDao;
import com.mzl.slideverifydemo1.modules.sys.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao extends CrudDao<User> {
    List<User> getUserByLoginName(User user);
}
