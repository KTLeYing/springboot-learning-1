package com.example.springdemo3.service.impl;

import com.example.springdemo3.dto.UserAllInfoDTO;
import com.example.springdemo3.entity.User;
import com.example.springdemo3.repository.UserRepository;
import com.example.springdemo3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;

/**
 * @ClassName :   UserServiceImpl
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/23 8:52
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    //注入依赖（直接利用jpa存储库）
    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        System.out.println("lllll");
        System.out.println(user);
        User user1 = userRepository.save(user);
        return user1;
    }

    //删除用户(使用user映射)
    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    //删除用户（使用id）
    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    //更新用户
    @Override
    public void updateUserById(User user) {
        userRepository.save(user);
    }

    //查找某个用户（用id）
    //请求参数id映射绑定函数参数id,函数参数与数据库参数已在实体通过注解映射绑定
    @Override
    public User findUserById(int id) {
        System.out.println("jjjj");
        System.out.println(id);
        return userRepository.findById(id).get();
    }

    //查询用户(使用Iterable，查询多个用户)
    @Override
    public Iterable<User> findUser(String ids) {
        System.out.println(ids);
        String[] arr = ids.split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String id : arr) {
            System.out.println(id);
            arrayList.add(Integer.parseInt(id));
        }
        Iterable<Integer> iterable = new ArrayList<Integer>(arrayList);
        return userRepository.findAllById(iterable);
        //或（用线性表数组，也可以，因为迭代器iterable是有线性表转换成的）
//        return userRepository.findAllById(arrayList);

    }

    //查询所有用户
    @Override
    public Iterable<User> findAllUser() {
        return userRepository.findAll();
    }

    //查询是否存在用户(用id)
    @Override
    public boolean exitUserById(int id) {
        return userRepository.existsById(id);
    }

    //查询用户（使用用户名）
    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    //查询用户的前一条记录(使用用户名)
    @Override
    public User findTopByName(String username) {
        return userRepository.findTopByUsername(username);
    }

    //查询用户（使用用户名和密码）
    @Override
    public User findByNameAndPsw(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    //查询用户（用用户名或密码）
    @Override
    public List<User> findNameOrPsw(String username, String password) {
        return userRepository.findByUsernameOrPassword(username, password);
    }

    //查询用户（手机号为空）
    @Override
    public List<User> findByPhoneNull() {
        return userRepository.findByPhoneIsNull();
    }

    //查询用户（手机号不为空）
    @Override
    public List<User> findByPhoneNotNull() {
        return userRepository.findByPhoneIsNotNull();
    }

    //查询用户（用用户名模糊查询）
    @Override
    public List<User> findByNameLike(String username) {
        return userRepository.findByUsernameLike("%" + username + "%");
    }

    //查询用户（以某个字符串开头）
    @Override
    public List<User> findByNameStartWith(String username) {
        return userRepository.findByUsernameStartingWith(username );
    }

    //查询用户（以某个字符串结尾）
    @Override
    public List<User> findByNameEndWith(String username) {
        return userRepository.findByUsernameEndingWith(username);
    }

    //查询用户（用户名包含某个字符串）
    @Override
    public List<User> findByNameContain(String username) {
        return userRepository.findByUsernameContaining(username);
    }

    //查询用户（用密码查询，用手机号来排序）
    @Override
    public List<User> findByPswOrderByPhone(String password) {
        return userRepository.findByPasswordOrderByPhoneDescUsernameDesc(password);
    }

    //查询用户(用户名不等于某个条件用户名的)
    @Override
    public List<User> findByNameNot(String username) {
        return userRepository.findByUsernameNot(username);
    }

    //查询用户（用集合里面的用户名）
    @Override
    public List<User> findByNameIn(String usernames) {
        System.out.println(usernames);
        String[] arr = usernames.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        for (String username : arr) {
            arrayList.add(username);
        }
        //把ArrayList转换为集合Collection
        Collection<String> collection = new ArrayList<String>(arrayList);
        System.out.println("collection:" + collection);
//        return userRepository.findByUsernameIn(collection);
        //或（用线性表数组，也可以，因为集合是有线性表转换成的）
        return userRepository.findByUsernameIn(arrayList);
    }

    //查询用户（不在集合里面的用户）
    @Override
    public List<User> findByNameNotIn(String usernames) {
        String[] arr = usernames.split(",");
        ArrayList<String> arrayList = new ArrayList<String>();
        for (String username : arr){
            arrayList.add(username);
        }
        //ArrayList转换为Collection
        Collection<String> collection = new ArrayList<>(arrayList);
        return userRepository.findByUsernameNotIn(collection);
        //或
//        return userRepository.findByUsernameNotIn(arrayList);
    }

    //查询用户（用用户名忽略大小写)
    @Override
    public List<User> findByNameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

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
    @Override
    public List<UserAllInfoDTO> findUserAllInfo() {
        List<UserAllInfoDTO> userAllInfoDTOList = new ArrayList<>();

        //利用object类型到jpa中访问数据
        List<Object> resultList = userRepository.findUserAllInfo();
        System.out.println(resultList.size());
        //遍历list列表,每个list下标元素代表着一行数据
        for (Object row : resultList){
            //object(list列表)转换为array数组，在一个获取array的下标值（即一行数据对应的属性值）
            Object[] arrRow = (Object[]) row;
            //根据数据库返回的列属性来对应吧数组的某个元素赋值给dto对象的对应属性
            UserAllInfoDTO userAllInfoDTO = new UserAllInfoDTO();
            userAllInfoDTO.setUserId((Integer) arrRow[0]);
            userAllInfoDTO.setUsername((String) arrRow[1]);
            userAllInfoDTO.setPhone((String) arrRow[2]);
            userAllInfoDTO.setCourseName((String) arrRow[3]);
            userAllInfoDTO.setTeacherName((String) arrRow[4]);
            userAllInfoDTO.setScore((Double) arrRow[5]);
            System.out.println(userAllInfoDTO);
            //把每个dto对象加入到要返回的list中
            userAllInfoDTOList.add(userAllInfoDTO);
        }

        //以dto列表的形式返回给前端
        return userAllInfoDTOList;
    }

    /**
     * ！！！注意，如果是查询非list(只返回一条记录，即一个Object)的多表联合查询直接返回多个表中的多个属性组合的原理和操作和以上方法List<Object>是一样的，
     * 只是业务逻辑层不用迭代遍历，直接把Object转换为数组来给DTO的对应属性赋值就行。
     */
    @Override
    public UserAllInfoDTO findOneUserAllInfo(Integer userId) {
        //直接查询某个对象
        Object userInfo = userRepository.findOneUserAllInfo(userId);
        //object(list列表)转换为array数组，在一个获取array的下标值（即一行数据对应的属性值）
        Object[] arrRow = (Object[]) userInfo;
        //根据数据库返回的列属性来对应吧数组的某个元素赋值给dto对象的对应属性
        UserAllInfoDTO userAllInfoDTO = new UserAllInfoDTO();
        userAllInfoDTO.setUserId((Integer) arrRow[0]);
        userAllInfoDTO.setUsername((String) arrRow[1]);
        userAllInfoDTO.setPhone((String) arrRow[2]);
        userAllInfoDTO.setCourseName((String) arrRow[3]);
        userAllInfoDTO.setTeacherName((String) arrRow[4]);
        userAllInfoDTO.setScore((Double) arrRow[5]);
        System.out.println(userAllInfoDTO);
        return userAllInfoDTO;
    }

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法2（方便）------------------------------------
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @Override
    public List<UserAllInfoDTO> findUserAllInfo1() {
        return userRepository.findUserAllInfo1();
    }

    /**查询非list也一样，原理是一样的，直接查询就行
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @Override
    public UserAllInfoDTO findOneUserAllInfo1(Integer userId) {
        return userRepository.findOneUserAllInfo1(userId);
    }

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法3（与object类似）------------------------------------
     * 使用Map<String, Object>，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @Override
    public List<Map<String, Object>> findUserAllInfo2() {
        return userRepository.findUserAllInfo2();
    }

    /** 使用Map<String, Object>查询一条记录，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @Override
    public Map<String, Object> findOneUserAllInfo2(Integer userId) {
        return userRepository.findOneUserAllInfo2(userId);
    }


}
