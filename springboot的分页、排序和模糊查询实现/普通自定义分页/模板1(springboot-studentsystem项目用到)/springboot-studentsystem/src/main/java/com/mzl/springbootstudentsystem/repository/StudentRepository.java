package com.mzl.springbootstudentsystem.repository;

import com.mzl.springbootstudentsystem.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @InterfaceName :   StudentRepository
 * @Description: 学生jpa存储库
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:29
 * @Version: 1.0
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {

    /**
     * 学生登录
     * @param account
     * @param password
     * @return
     */
    Student findByAccountAndPassword(String account, String password);

    /**
     * 通过账号来查询学生
     * @param account
     * @return
     */
    Student findByAccount(String account);

    /**
     * JPQL和原生的SQL区别：
     * @Query注解查询使用于所查询的数据无法通过关键字查询得到结果的查询。这种查询可以摆脱像关键字查询那样的约束，
     * 将查询直接在相应的接口方法中声明，结构更为清晰，这是Spring Data的特有实现。
     * JPQL并不支持INSERT语句，但可以使用UPDATE和DELETE语句，要想使用UPDATE或DELETE语句则需要在@Query注解上@Modifying注解，以通知该JPQL为更新或删除操作。
     * 注:使用JPQL语句来更新或删除操作，会使用事务。此时需要开启事务来使用。否则会报不支持DML错误。此时可以采用Spring的基于AOP的XML配置的
     * 声明式事务或@Transactional注解的事务[使用注解方式需spring开启事务注解驱动]。
     * 注释：当设置nativeQuery=true即可以使用原生SQL进行查询
     * @Modifying
     * 1：在@Query注解中编写JPQL实现DELETE和UODATE操作的时候必须加上@Modifying注解，以通知Springdata这是一个DELETE或UPDATE操作。
     * 2：UPDATE或DELETE操作需要使用事务，此时需要定义Service层，在Service层的方法上添加事务操作。
     * 3：注意JOQL不支持INSERT操作。
     */
    /**
     * 通过姓名查询学生
     * @param name
     * @return
     */
    //法1：原生的sql,nativeQuery = true
    //占位符形式:
//    @Query(name = "findStuByName", nativeQuery = true, value = "select * from student where name = ?1 ")  //此次是原生的SQL，nativeQuery = true
//    Student findStuByName(String name);
    //命名参数形式:
//    @Query(name = "findStuByName", nativeQuery = true, value = "select * from student where name = :name")  //此次是原生的SQL，nativeQuery = true
//    Student findStuByName(@Param("name") String name);

    //法2：JPQL,用实体类名代替数据库表名
    //占位符形式:
//    @Query("select s from Student s where s.name = ?1")
//    Student findStuByName(String name);
    //命名参数形式：
    @Query("select s from Student s where s.name = :name")
    Student findStuByName(@Param("name") String name);

    /**
     * 模糊查询学生
     * @param
     * @return
     */
    @Query("select s from Student s where s.account like %?1% and s.name like %?2%")
    List<Student> findStuByCondition(String account, String name);

    /**
     *  查询总记录数
     * @return
     */
    @Query("select count(s) from Student s where s.account like %?1% and s.name like %?2%")
    int findTotalCount(String account, String name);

    /**
     * 查询分页列表
     * @param start
     * @param pageSize
     * @param account
     * @param name
     * @return
     */
    @Query(name = "findStuList", nativeQuery = true, value = "select * from student where account like %?3% and name like %?4% limit ?1, ?2")
    List<Student> findStuList(Integer start, Integer pageSize, String account, String name);

    /**
     * 分页和排序查询（使用jpa自带的pageable分页和排序功能，在方法名里使用排序OrderBy）
     * @param pageable
     * @return
     */
    Page<Student> findByOrderByAgeDesc(Pageable pageable);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，排序是在pageable使用Sort）
     * @param s
     * @param pageable
     * @return
     */
    Page<Student> findByNameContaining(String s, Pageable pageable);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，排序是在pageable使用Sort）
     * @param
     * @param pageable
     * @return
     */
    Page<Student> findByNameLike(String s, Pageable pageable);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，在方法名里使用排序OrderBy）
     * @param
     * @param pageable
     * @return
     */
    Page<Student> findByNameContainingOrderByAgeDesc(String s, Pageable pageable);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，在方法名里使用排序OrderBy）
     * @param s
     * @param pageable
     * @return
     */
    Page<Student> findByNameLikeOrderByAgeDesc(String s, Pageable pageable);
}
