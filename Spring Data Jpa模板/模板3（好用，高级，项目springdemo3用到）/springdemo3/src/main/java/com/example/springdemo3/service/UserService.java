package com.example.springdemo3.service;

import com.example.springdemo3.dto.UserAllInfoDTO;
import com.example.springdemo3.entity.User;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: 21989
 * @CreateDate: 2020/7/23 8:53
 * @Version: 1.0
 */
public interface UserService{

    //添加用户
    User saveUser(User user);

    //删除用户(用user来映射id)
    void deleteUser(User user);

    //删除用户（使用id）
    void deleteUserById(int id);

    //查找用户
    User findUserById(int id);

    //更新用户
    void updateUserById(User user);

    //查询用户信息(使用Iterable,多个用户)
    Iterable<User> findUser(String ids);

    //查询所有用户
    Iterable<User> findAllUser();

    //查询用户是否存在(用id)
    boolean exitUserById(int id);

    //查询用户（使用用户名）
    User findUserByName(String username);

    //查询用户的前一条记录
    User findTopByName(String username);

    //查询用户（使用用户名和密码）
    User findByNameAndPsw(String username, String password);

    //查询用户（用用户名或密码）
    List<User> findNameOrPsw(String username, String password);

    //查询用户（手机号为空）
    List<User> findByPhoneNull();

    //查询用户（手机号不为空）
    List<User> findByPhoneNotNull();

    //查询用户（用用户名模糊查询）
    List<User> findByNameLike(String username);

    //查询用户（以某个字符串开头）
    List<User> findByNameStartWith(String username);

    //查询用户（以某个字符串结尾）
    List<User> findByNameEndWith(String username);

    //查询用户（用户名包含某个字符串）
    List<User> findByNameContain(String username);

    //查询用户（用密码查询，用手机号来排序）
    List<User> findByPswOrderByPhone(String password);

    //查询用户(用户名不等于某个条件用户名的)
    List<User> findByNameNot(String username);

    //查询用户（用集合里面的用户名）
    List<User> findByNameIn(String usernames);

    //查询用户（不在集合里面的用户）
    List<User> findByNameNotIn(String usernames);

    //查询用户（用用户名忽略大小写)
    List<User> findByNameIgnoreCase(String username);

    /***----------多表联合查询直接返回多个表中的多个属性组合：方法1------------------------------------
     * spring data jpa使用@Query多表联合查询,
     * JpaRepository的接口类只能返回的对应实体类DbCollectProduction对象或DbCollectProduction类里面的属性，一定不能返回其他的实体类，不然报错，
     * 因为jpa定义指定的实体类与真正返回的实体类对应不上。
     * 【### 但是也可以提供下面的方法多表联合查询直接返回多个表中的多个属性组合，通可以再JpaRepository先返回Object，
     * 然后再在serviceImpl层来处理Obiect（是list来的，每个list下标元素代表着一行数据），先把object转换为array数组，
     * 在一个获取array的下标值（即一行数据对应的属性值），最后通过自定义的dto数据传输对象返回到前端。】
     * (JPQL和原生的SQL都可以使用)
     * @param
     * @return
     */
    List<UserAllInfoDTO> findUserAllInfo();

    /**
     * ！！！注意，如果是查询非list(只返回一条记录，即一个Object)的多表联合查询直接返回多个表中的多个属性组合的原理和操作和以上方法List<Object>是一样的，
     * 只是业务逻辑层不用迭代遍历，直接把Object转换为数组来给DTO的对应属性赋值就行。
     */
    UserAllInfoDTO findOneUserAllInfo(Integer userId);

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法2（方便）------------------------------------
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    List<UserAllInfoDTO> findUserAllInfo1();

    /**查询非list也一样，原理是一样的，直接查询就行
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    UserAllInfoDTO findOneUserAllInfo1(Integer userId);

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法3（与object类似）------------------------------------
     * 使用Map<String, Object>，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    List<Map<String, Object>> findUserAllInfo2();

    /** 使用Map<String, Object>查询一条记录，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    Map<String, Object> findOneUserAllInfo2(Integer userId);

}
