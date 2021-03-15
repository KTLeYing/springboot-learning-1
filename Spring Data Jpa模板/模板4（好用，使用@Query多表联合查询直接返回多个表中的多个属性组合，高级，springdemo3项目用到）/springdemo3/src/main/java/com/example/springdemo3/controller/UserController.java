package com.example.springdemo3.controller;

import com.example.springdemo3.dto.UserAllInfoDTO;
import com.example.springdemo3.entity.User;
import com.example.springdemo3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName :   UserController
 * @Description: 用户接口
 * @Author: 21989
 * @CreateDate: 2020/7/23 9:00
 * @Version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //注入依赖
    @Autowired
    private UserService userService;

    //添加用户
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public User addUser(User user){
        System.out.println("yyyyyyy");
        System.out.println("user:" + user);
        User user1 = userService.saveUser(user);
        System.out.println("ddddd");
        System.out.println("user1:" + user1);
        return user1;
    }

    //更新用户
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public String updateUserById(User user){
        userService.updateUserById(user);
        return "SUCCESS";
    }

    //删除用户（使用user映射）
    @RequestMapping("/deleteUser")
    public String deleteUser(User user){
        userService.deleteUser(user);
        return "SUCCESSS";
    }

    //删除用户（使用id）
    @RequestMapping("/deleteUserById")
    public String deleteUserById(int id){
        userService.deleteUserById(id);
        return "SUCCESS";
    }

    //查询用户(使用用户id)
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public User findUserById(int id){
        System.out.println(id);
        User user = userService.findUserById(id);
        System.out.println(user);
        return user;
    }

    //查询用户信息(使用Itreable,删除多个用户)
    @RequestMapping("/findUser")
    public Iterable<User> findUser(String ids){
        Iterable<User> users = userService.findUser(ids);
        System.out.println(users);
        return users;
    }

    //查询所有用户
    @RequestMapping("/findAllUser")
    public Iterable<User> findAllUser(){
        return userService.findAllUser();
    }

    //查询用户是否存在(用id)
    @RequestMapping("/exitUserById")
    public boolean exitUserById(int id){
        return userService.exitUserById(id);
    }

    //查询用户（使用用户名）
    @RequestMapping("/findUserByName")
    public User findUserByName(String username){
        return userService.findUserByName(username);
    }

    //查询用户的前一条记录(使用用户名)
    @RequestMapping("/findTopByName")
    public User findTopByName(String username){
        return userService.findTopByName(username);
    }

    //查询用户（使用用户名和密码）
    @RequestMapping("/findNameAndPsw")
    public User findByNameAndPsw(String username, String password){
        return userService.findByNameAndPsw(username, password);
    }

    //查询用户（用用户名或密码）
    @RequestMapping("/findByNameOrPsw")
    public List<User> findByNameOrPsw(String username, String password){
        return userService.findNameOrPsw(username, password);
    }

    //查询用户（手机号为空）
    @RequestMapping("/findByPhoneNull")
    public List<User> findByPhoneNull(){
        return userService.findByPhoneNull();
    }

    //查询用户（手机号不为空）
    @RequestMapping("/findByPhoneNotNull")
    public List<User> findByPhoneNotNull(){
        return userService.findByPhoneNotNull();
    }

    //查询用户（用用户名模糊查询）
    @RequestMapping("/findByNameLike")
    public List<User> findByNameLike(String username){
        return userService.findByNameLike(username);
    }

    //查询用户（用户名以某个字符串开头）
    @RequestMapping("/findByNameStartWith")
    public List<User> findByNameStartWith(String username){
        return userService.findByNameStartWith(username);
    }

    //查询用户（用户名以某个字符串结尾）
    @RequestMapping("/findByNameEndWith")
    public List<User> findByNameEndWith(String username){
        return userService.findByNameEndWith(username);
    }

    //查询用户（用户名包含某个字符串）
    @RequestMapping("/findByNameContain")
    public List<User> findByNameContain(String username){
        return userService.findByNameContain(username);
    }

    //查询用户（用密码查询，用手机号来排序）
    @RequestMapping("/findByPswOrderByPhone")
    public List<User> findByPswOrderByPhone(String password){
        return userService.findByPswOrderByPhone(password);
    }

    //查询用户(用户名不等于某个条件用户名的)
    @RequestMapping("/findByNameNot")
    public List<User> findByNameNot(String username){
        return userService.findByNameNot(username);
    }

    //查询用户（用集合里面的用户名）
    @RequestMapping("/findByNameIn")
    public List<User> findByNameIn(String usernames){
        return userService.findByNameIn(usernames);
    }

    //查询用户（不在集合里面的用户）
    @RequestMapping("/findByNameNotIn")
    public List<User> findByNameNotIn(String usernames){
        return userService.findByNameNotIn(usernames);
    }

    //查询用户（用用户名忽略大小写)
    @RequestMapping("/findByNameIgnoreCase")
    public List<User> findByNameIgnoreCase(String username){
        return userService.findByNameIgnoreCase(username);
    }

     /***----------多表联合查询直接返回多个表中的多个属性组合：方法1------------------------------------
     * spring data jpa使用@Query多表联合查询,
     * JpaRepository的接口类只能返回的对应实体类DbCollectProduction对象或DbCollectProduction类里面的属性，一定不能返回其他的实体类，不然报错，
     * 因为jpa定义指定的实体类与真正返回的实体类对应不上。
     * 【### 但是也可以提供下面的方法多表联合查询直接返回多个表中的多个属性组合，通可以再JpaRepository先返回Object，
     * 然后再在serviceImpl层来处理Obiect（是list来的，每个list下标元素代表着一行数据），先把object转换为array数组，
      * （但是要注意返回的字段是否为空，否则出现空指针异常）
     * 在一个获取array的下标值（即一行数据对应的属性值），最后通过自定义的dto数据传输对象返回到前端。】
      *(JPQL和原生的SQL都可以使用)
     * @param
     * @return
     */
    @RequestMapping("/findUserAllInfo")
    public List<UserAllInfoDTO> findUserAllInfo(){
        return userService.findUserAllInfo();
    }

    /**
     * ！！！注意，如果是查询非list(只返回一条记录，即一个Object)的多表联合查询直接返回多个表中的多个属性组合的原理和操作和以上方法List<Object>是一样的，
     * 只是业务逻辑层不用迭代遍历，直接把Object转换为数组来给DTO的对应属性赋值就行。（但是要注意返回的字段是否为空，否则出现空指针异常）
     */
    @RequestMapping("/findOneUserAllInfo")
    public UserAllInfoDTO findOneUserAllInfo(Integer userId){
        return userService.findOneUserAllInfo(userId);
    }


    /**----------多表联合查询直接返回多个表中的多个属性组合：方法2（方便）------------------------------------
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @RequestMapping("/findUserAllInfo1")
    public List<UserAllInfoDTO> findUserAllInfo1(){
        return userService.findUserAllInfo1();
    }

    /**查询非list也一样，原理是一样的，直接查询就行
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @RequestMapping("/findOneUserAllInfo1")
    public UserAllInfoDTO findOneUserAllInfo1(Integer userId){
        return userService.findOneUserAllInfo1(userId);
    }


    /**----------多表联合查询直接返回多个表中的多个属性组合：方法3（与object类似）------------------------------------
     * 使用Map<String, Object>，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @RequestMapping("/findUserAllInfo2")
    public List<Map<String, Object>> findUserAllInfo2(){
        return userService.findUserAllInfo2();
    }

    /** 使用Map<String, Object>查询一条记录，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @RequestMapping("/findOneUserAllInf2")
    public Map<String, Object> findOneUserAllInfo2(Integer userId){
        return userService.findOneUserAllInfo2(userId);
    }


}
