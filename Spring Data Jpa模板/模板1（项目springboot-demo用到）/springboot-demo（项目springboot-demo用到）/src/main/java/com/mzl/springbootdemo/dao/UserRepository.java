package com.mzl.springbootdemo.dao;

import com.mzl.springbootdemo.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @InterfaceName :   UserRepository
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/23 15:24
 * @Version: 1.0
 */
// 继承CrudRepository接口，<实体类, 主键类型>
// JPA根据实体类的类名去对应表名（可以使用@Entity的name属性？@Table进行修改）
public interface UserRepository extends CrudRepository<User, Integer> {

    //自定义的jpa,使用用户名查询用户
    User findByUsername(String username);
}
