package com.mzl.springbootdemo1.repository;

import com.mzl.springbootdemo1.entity.User;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @InterfaceName :   UserRepository
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:28
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<User, Integer>{

}
