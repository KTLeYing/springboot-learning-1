package com.mzl.springbootstudentsystem.service;

import com.mzl.springbootstudentsystem.entity.PageBean;
import com.mzl.springbootstudentsystem.entity.Student;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @InterfaceName :   UserService
 * @Description: 学生业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:20
 * @Version: 1.0
 */
public interface StudentService {



    /**
     * 学生登录
     * @param student
     * @return
     */
    Student login(Student student);

    /**
     * 查询所有学生
     * @return
     */
    List<Student> findAllStu();

    /**
     * 通过用户id来查询学生信息
     * @param id
     * @return
     */
    Student findById(int id);

    /**
     * 通过账号来查询学生
     * @param account
     * @return
     */
    Student findByAccount(String account);

    /**
     * 通过姓名查询学生
     * @param name
     * @return
     */
    Student findByName(String name);

    /**
     * 学生注册（添加学生）
     * @param student
     */
    void addStudent(Student student);

    /**
     * 修改学生信息
     * @param student
     */
    void updateStudent(Student student);

    /**
     * 用id删除学生
     * @param id
     */
    void deleteStudent(int id);

    /**
     * 批量删除学生（删除选中）
     * @param arrayList
     */
    void deleteManyStu(ArrayList<Integer> arrayList);

    /**
     * 模糊查询学生
     * @param student
     * @return
     */
    List<Student> findStuByCondition(Student student);

    /**
     * 分页查询学生（使用jpa的自定义分页）
     * @param curPage
     * @param pageSize
     * @return
     */
    PageBean<Student> findAllStuByPage(Integer curPage, Integer pageSize, Student student);

    /**
     * 分页查询（使用jpa自带的pageable分页功能）
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<Student> findByPage(Integer curPage, int pageSize);

    /**
     * 分页和排序查询（使用jpa自带的pageable分页和排序功能，在pageable使用Sort排序）
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<Student> findByPageAndSort(Integer curPage, Integer pageSize);

    /**
     * 分页和排序查询（使用jpa自带的pageable分页和排序功能，在方法名里使用排序OrderBy）
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<Student> findByPageAndSort1(Integer curPage, Integer pageSize);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，排序是在pageable使用Sort）
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<Student> findByPageAndSortAndMh(Integer curPage, Integer pageSize);

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，在方法名里使用排序OrderBy）
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<Student> findByPageAndSortAndMh1(Integer curPage, Integer pageSize);
}
