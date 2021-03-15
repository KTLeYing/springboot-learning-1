package com.example.mybatisplusdemo1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mybatisplusdemo1.dto.UserDTO;
import com.example.mybatisplusdemo1.entity.User;
import com.example.mybatisplusdemo1.mapper.UserMapper;
import com.example.mybatisplusdemo1.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName :   UserServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/5 2:23
 * @Version: 1.0
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有用户(普通的使用mybatis自定义的方法)
     * @return
     */
    @Override
    public List<User> userList() {
        //这里是普通的mybatis自定义的方法
        return userMapper.userList();
    }

    /**
     * 获取所有用户(有条件/无条件，使用mybatis-plus自带的方法)
     * @return
     */
    @Override
    public List<User> findUserList() {
        QueryWrapper queryWrapper = new QueryWrapper();
//        queryWrapper.eq("username1", "li");  //设置条件，含有li的
        return userMapper.selectList(null);  //无条件
//        return userMapper.selectList(queryWrapper);   //有条件
    }

    /**
     * 通过用户主键id查找用户(使用mybatis-plus自带的方法)
     * @param
     * @return
     */
    @Override
    public User findUserById(int id) {
        //使用mybatis-plus自带的方法，返回值是list
        return userMapper.selectById(id);
    }

    /**
     * 通过用户名查找用户(使用mybatis-plus自带的方法)
     * @param username
     * @return
     */
    @Override
    public List<User> findUserByName(String username) {
        //在mybatis-plus里用map来封装参数,可以封装多个查询条件
        Map map = new HashMap();
        map.put("username", username);  //key是数据库表的字段
//        map.put("sex", sex);
        //使用mybatis-plus自带的方法，返回值是list
        return userMapper.selectByMap(map);
    }

    /**
     * 模糊查找用户姓名含有li且年龄大于等于18且性别是男的，多条件复杂查询(使用mybatis-plus自带的方法)
     * @param sex
     * @param age
     * @return
     */
    @Override
    public List<User>  findUserByComplexCondition(String username, String sex, Integer age) {
        //经常查询条件包装
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.eq("sex", sex);
        queryWrapper.gt(true, "age", age);
        return userMapper.selectMaps(queryWrapper);
    }

    /**
     * 插入保存用户(使用mybatis-plus自带的方法)
     * @param user
     * @return
     */
    @Override
    public int saveUser(User user) {
        //使用mybatis-plus自带的方法，返回值是插入的行数
        return userMapper.insert(user);
    }

    /**
     * 修改用户(使用mybatis-plus自带的方法)
     * @param user
     * @return
     */
    @Override
    public Integer updateUser(User user) {
        //先查询用户(通过主键标识)
        User user1 = userMapper.selectById(user.getId());
        //把user的属性同名映射赋值给user1
        BeanUtils.copyProperties(user, user1);
        return userMapper.updateById(user);
    }

    /**
     * 删除用户(使用mybatis-plus自带的方法)
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(Integer id) {
        //返回int
        return userMapper.deleteById(id);
    }

    /**
     * 用户名删除用户(使用mybatis-plus自带的方法)
     * @param username
     * @return
     */
    @Override
    public int deleteUserByName(String username) {
        Map map = new HashMap();
        map.put("username", username);  //设置参数，key是数据库表字段
        return userMapper.deleteByMap(map);
    }

    /**
     * 用id批量删除用户(使用mybatis-plus自带的方法)
     * @param ids
     * @return
     */
    @Override
    public int deleteUsers(Integer[] ids) {
        List idList = new ArrayList(Arrays.asList(ids));
        return userMapper.deleteBatchIds(idList);
    }

    /**
     * 统计用户总数(有条件和无条件,使用mybatis-plus自带的方法)
     * @return
     */
    @Override
    public Integer userCount(Integer age) {
        //创建条件构造器,messageQueryWrapper.eq(“nickname”,“sunshine”);
        //其中，第一个参数为数据库中的字段名称，记住，是数据库中的字段第二个参数为要查询的内容
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        //如果统计所有，无条件，则不用设置了，表示相等
//        userQueryWrapper.eq("age", age);
        //表示大于
//        userQueryWrapper.ge(true,"age", age);  //表示大于等于(包含边界)
        userQueryWrapper.gt(true,"age", age);  //表示大于(不包含边界)

//        userQueryWrapper.le(true,"age", age);  //表示小于等于(包含边界)
//        userQueryWrapper.lt(true,"age", age);  //表示小于(不包含边界)

        return userMapper.selectCount(userQueryWrapper);

    }

    /**
     * 分页查询用户(使用自定义的sql分页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<User> findUserByPage(Integer pageNum, Integer pageSize, Integer age) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        //无查询条件的(分页查询所有的)
        return userMapper.selectPage(page, null);
    }

    /**
     * 分页查询用户(无条件，使用自定义的sql分页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<User> findUserByPage1(Integer pageNum, Integer pageSize, Integer age, String username) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("age", age);  //年龄大于age
        queryWrapper.like("username", username);  //姓名含有li的
        //有查询条件的
        return userMapper.selectPage(page, queryWrapper);
    }

    /**
     * 分页查询用户(无条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<User> findUserByPage2(Integer pageNum, Integer pageSize) {
        //设置页码和条数，这一行代码就实现了分页，而我做了一个判断的原因是，如若数据是要不分页展示所有的，那就不需要启动这行代码。
        PageHelper.startPage(pageNum, pageSize);
        //返回的数据
        QueryWrapper queryWrapper = new QueryWrapper();
        List<User> userList = userMapper.selectList(null);
        return new PageInfo<>(userList);
    }

    /**
     * 分页查询用户(有条件，使用PageHelper分页插件)
     * @param pageNum
     * @param pageSize
     * @param age
     * @param username
     * @return
     */
    @Override
    public PageInfo<User> findUserByPage3(Integer pageNum, Integer pageSize, Integer age, String username) {
        //设置页码和条数，这一行代码就实现了分页，而我做了一个判断的原因是，如若数据是要不分页展示所有的，那就不需要启动这行代码。
        PageHelper.startPage(pageNum, pageSize);
        //返回的数据
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("username", username);  //模糊查询，用户名含有li的
        queryWrapper.gt("age", age);  //年龄大于18
        List<User> userList = userMapper.selectList(queryWrapper);
        return new PageInfo<>(userList);
    }

    /**
     * 查询直接返回一个用户dto
     * @return
     */
    @Override
    public List<UserDTO> findUserDTO() {
        return userMapper.findUserDTO();
    }


}
