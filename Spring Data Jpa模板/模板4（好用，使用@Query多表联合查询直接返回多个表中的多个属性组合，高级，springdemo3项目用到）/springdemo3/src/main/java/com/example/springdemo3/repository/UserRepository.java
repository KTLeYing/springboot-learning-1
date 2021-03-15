package com.example.springdemo3.repository;

import com.example.springdemo3.dto.UserAllInfoDTO;
import com.example.springdemo3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @InterfaceName :   UserRepository
 * @Description: 用户jpa存储库接口（serviceImpl层直接利用里面的方法）
 * @Author: 21989
 * @CreateDate: 2020/7/23 9:14
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<User, Integer>, CrudRepository<User, Integer> {

    //使用用户名查询用户
    User findByUsername(String username);

    //查询用户的前一条记录(使用用户名)
    User findTopByUsername(String username);

    //查询用户（使用用户名和密码）
    User findByUsernameAndPassword(String username, String password);

    //查询用户（用用户名或密码）
    List<User> findByUsernameOrPassword(String username, String password);

    //查询用户（手机号为空）
    List<User> findByPhoneIsNull();

    //查询用户（手机号不为空）
    List<User> findByPhoneIsNotNull();

    //查询用户（用用户名模糊查询）
    List<User> findByUsernameLike(String s);

    //查询用户（以某个字符串开头）
    List<User> findByUsernameStartingWith(String username);

    //查询用户（以某个字符串结尾）
    List<User> findByUsernameEndingWith(String username);

    //查询用户（用户名包含某个字符串）
    List<User> findByUsernameContaining(String username);

    //查询用户（用密码查询，用手机号来排序）
    List<User> findByPasswordOrderByPhoneDescUsernameDesc(String password);

    //查询用户(用户名不等于某个条件用户名的)
    List<User> findByUsernameNot(String username);

    //查询用户（用集合里面的用户名）
//    List<User> findByUsernameIn(Collection<String> collection);
    //或（用线性表数组，也可以，因为集合是有线性表转换成的）
    List<User> findByUsernameIn(ArrayList<String> arrayList);

    //查询用户（不在集合里面的用户）
    List<User> findByUsernameNotIn(Collection<String> collection);
    //或
//    List<User> findByUsernameNotIn(ArrayList<String> arrayList);

    //查询用户（用用户名忽略大小写)
    List<User> findByUsernameIgnoreCase(String username);


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
    @Query(nativeQuery = true, value = "select u.id as userId, u.username, u.phone, c.courseName, t.teacherName, s.score" +
            " from user u, course c, score s, teacher t where u.id = c.userId and c.id = s.courseId and c.id = t.courseId")
    List<Object> findUserAllInfo();
//    或（用实体类代替表,此时是返回多表的多个属性，所以@Query中一定要使用nativeQuery，不然查询结果为空）
//    @Query(nativeQuery = true, value = "select u.id as userId, u.username, u.phone, c.courseName, t.teacherName, s.score" +
//            " from User u, Course c, Score s, Teacher t where u.id = c.userId and c.id = s.courseId and c.id = t.courseId")
//    List<Object> findUserAllInfo();

    /**
     * ！！！注意，如果是查询非list(只返回一条记录，即一个Object)的多表联合查询直接返回多个表中的多个属性组合的原理和操作和以上方法List<Object>是一样的，
     * 只是业务逻辑层不用迭代遍历，直接把Object转换为数组来给DTO的对应属性赋值就行。
     */
    @Query(nativeQuery = true, value = "select u.id as userId, u.username, u.phone, c.courseName, t.teacherName, s.score" +
            " from user u, course c, score s, teacher t where u.id = c.userId and c.id = s.courseId and c.id = t.courseId and u.id = ?1")
    Object findOneUserAllInfo(Integer userId);

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法2（方便）------------------------------------
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @Query("select new com.example.springdemo3.dto.UserAllInfoDTO(u.id, u.username, u.phone, c.courseName, t.teacherName, s.score) " +
            " from User u left join Course c on u.id = c.userId left join Score s on c.id = s.courseId left join Teacher t on c.id = t.courseId")
    List<UserAllInfoDTO> findUserAllInfo1();

    /**查询非list也一样，原理是一样的，直接查询就行
     * 直接使用DTO传输对象的构造方法，在JPQL查询时直接new创建严格dto对象并把相关的属性投影到相应的构造方法的参数里面，
     * 然后给构造方法里面的属性赋值,这样就可以间接在repository里面使用自定义的DTO对象了
     * (可以在dto里面定义多个不同参数的构造方法去使用,只能使用JPQL，不能用原生的SQL,
     * 数据库表字段名一定要用下划线形式命名，而实体类对应用驼峰式命名，不然会导致查询结果出错为空)
     * @return
     */
    @Query("select new com.example.springdemo3.dto.UserAllInfoDTO(u.id, u.username, c.courseName, t.teacherName, s.score) " +
            "from User u left join Course c on u.id = c.userId left join Score s on c.id = s.courseId left join Teacher t on c.id = t.courseId where u.id = ?1")
    UserAllInfoDTO findOneUserAllInfo1(Integer userId);

    /**----------多表联合查询直接返回多个表中的多个属性组合：方法3（与object类似）------------------------------------
     * 使用Map<String, Object>，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @Query(nativeQuery = true, value = "select u.id, u.username, u.phone, c.course_name, t.teacher_name, sc.score" +
            " from user u left join course c on u.id = c.user_id " +
            "left join score sc on sc.course_id = c.id " +
            "left join teacher t on t.course_id = c.id")
    List<Map<String, Object>> findUserAllInfo2();

    /** 使用Map<String, Object>查询一条记录，如果是返回多条数据，则使用List接收Map，即List<Map<String, Object>>,
     * jpa查询自动调用put()函数把字段列名赋值给Map的key，把字段名对应的值赋给Map的value，最后通过get()函数获取map的值来给
     * 自定义DTO设置属性值，把dto数据传输对象返回到前端。（原理跟Object有点相似，但是要注意返回的字段是否为空，否则出现空指针异常）
     * (只能用原生的SQL，不能用JPQL)
     * @return
     */
    @Query(nativeQuery = true, value = "select u.id, u.username, u.phone, c.course_name, t.teacher_name, sc.score" +
            " from user u left join course c on u.id = c.user_id " +
            "left join score sc on sc.course_id = c.id " +
            "left join teacher t on t.course_id = c.id where u.id = ?1")
    Map<String, Object> findOneUserAllInfo2(Integer userId);
}
