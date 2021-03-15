package com.mzl.springbootstudentsystem.service.impl;

import com.mzl.springbootstudentsystem.entity.PageBean;
import com.mzl.springbootstudentsystem.entity.Student;
import com.mzl.springbootstudentsystem.repository.StudentRepository;
import com.mzl.springbootstudentsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   StudentServiceImpl
 * @Description: 学生业务逻辑
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:20
 * @Version: 1.0
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    //注入依赖
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 学生登录
     * @param student
     * @return
     */
    @Override
    public Student login(Student student) {
        return studentRepository.findByAccountAndPassword(student.getAccount(), student.getPassword());
    }

    /**
     * 查询所有学生
     * @return
     */
    @Override
    public List<Student> findAllStu() {
        return studentRepository.findAll();
    }

    /**
     *通过用户id来查询学生信息
     * @param id
     * @return
     */
    @Override
    public Student findById(int id) {
        return studentRepository.findById(id).get();
    }

    /**
     * 通过账号来查询学生
     * @param account
     * @return
     */
    @Override
    public Student findByAccount(String account) {
        return studentRepository.findByAccount(account);
    }

    /**
     * 通过姓名查询学生
     * @param name
     * @return
     */
    @Override
    public Student findByName(String name) {
        return studentRepository.findStuByName(name);
    }

    /**
     * 学生注册（添加学生）
     * @param student
     */
    @Override
    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    /**
     * 修改学生信息
     * @param student
     */
    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    /**
     * 用id删除学生
     * @param id
     */
    @Override
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }

    /**
     * 批量删除学生（删除选中）
     * @param arrayList
     */
    @Override
    public void deleteManyStu(ArrayList<Integer> arrayList) {
        for (int id : arrayList) {
            studentRepository.deleteById(id);
        }
    }

    /**
     * 模糊查询学生
     * @param student
     * @return
     */
    @Override
    public List<Student> findStuByCondition(Student student) {
        return studentRepository.findStuByCondition(student.getAccount(),student.getName());
    }

    /**
     * 分页查询学生(使用普通的自定义的分页)
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Student> findAllStuByPage(Integer curPage, Integer pageSize, Student student) {
        System.out.println(student);
        if (curPage == null){
            curPage = 0;
        }
        if (pageSize == null){
            pageSize = 5;
        }
        //如果条件为空或为空值，设置为空字符串
        if (student.getAccount() == null || student.getAccount().equals("")){
            student.setAccount("");
        }
        if (student.getName() == null || student.getName().equals("")){
            student.setName("");
        }

        //创建分页对象
        PageBean<Student> pageBean = new PageBean<>();

        //查询总记录数
        int totalCount = studentRepository.findTotalCount(student.getAccount(), student.getName());
        System.out.println(totalCount);
        pageBean.setTotalCount(totalCount);

        //总页数
        int totalPage = 0;
        if (totalCount % pageSize == 0){
            totalPage = totalCount / pageSize;
        }else {
            totalPage = totalCount / pageSize + 1;
        }
        pageBean.setTotalPage(totalPage);

        //设置当前页数
        if (curPage > totalPage){
            curPage = totalPage;
        }
        if (curPage < 0){
            curPage = 0;
        }
        pageBean.setCurPage(curPage);

        //设置每页记录数
        pageBean.setPageSize(pageSize);

        //设置开始位置
        int start = pageSize * curPage;
        pageBean.setStart(start);

        //查询分页列表
        List<Student> studentList = new ArrayList<Student>();
        studentList = studentRepository.findStuList(start, pageSize, student.getAccount(), student.getName());
        System.out.println(studentList);
        pageBean.setPageList(studentList);

        System.out.println(pageBean);
        return pageBean;
    }

    /**
     * 分页查询（使用jpa自带的pageable分页功能）
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPage(Integer curPage, int pageSize) {
        Pageable pageable = PageRequest.of(curPage, pageSize);
        return studentRepository.findAll(pageable);
    }

    /**
     * 分页和排序查询（使用jpa自带的pageable分页和排序功能，在pageable使用Sort排序）
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPageAndSort(Integer curPage, Integer pageSize){
        //Sort.Direction.DESC是降序，Sort.Direction.ASC是升序，properties是用于排序的实体属性,这里是用年龄age属性进行排序
        Sort sort = Sort.by(Sort.Direction.DESC, "age","id"); //这里多个排序条件用逗号隔开，先用age排，如果age相同的再使用id排
        Pageable pageable = PageRequest.of(curPage, pageSize, sort);
        return studentRepository.findAll(pageable);
    }

    /**
     * 分页和排序查询（使用jpa自带的pageable分页和排序功能，在方法名里使用排序OrderBy）
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPageAndSort1(Integer curPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(curPage, pageSize);
        return studentRepository.findByOrderByAgeDesc(pageable);   //Desc为降序，Asc为升序
    }

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，排序是在pageable使用Sort）
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPageAndSortAndMh(Integer curPage, Integer pageSize) {
        //Sort.Direction.DESC是降序，Sort.Direction.ASC是升序，properties是用于排序的实体属性,这里是用年龄age属性进行排序
        Sort sort = Sort.by(Sort.Direction.DESC, "age","id"); //这里多个排序条件用逗号隔开，先用age排，如果age相同的再使用id排
        Pageable pageable = PageRequest.of(curPage, pageSize, sort);

        //查询含有李字的姓名，排序并分页显示
        return studentRepository.findByNameContaining("李", pageable); //Containing不用加%号，自动底层内部已包含了%...%了
        //或
//        return studentRepository.findByNameLike("%李%", pageable);  //方法参数如下，一定要加%号，比如 "%"+name+"%"
    }

    /**
     * 分页+排序+模糊查询（使用jpa自带的pageable分页、排序和模糊功能，在方法名里使用排序OrderBy）
     * @param curPage
     * @param pageSize
     * @return
     */
    @Override
    public Page<Student> findByPageAndSortAndMh1(Integer curPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(curPage, pageSize);

        //查询含有李字的姓名，排序并分页显示
        return studentRepository.findByNameContainingOrderByAgeDesc("李", pageable); //Containing不用加%号，自动底层内部已包含了%...%了
        //或
//        return studentRepository.findByNameLikeOrderByAgeDesc("%李%", pageable);  //方法参数如下，一定要加%号，比如 "%"+name+"%"
    }




    /**
     * jpa自动的分页排序注意（易错点）：
     * 过时方法（会报错）：
     * Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
     * Pageable pageable = new PageRequest(0, size, sort);
     *
     * 要改为新方法：
     * Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
     *  Pageable pageable= PageRequest.of(0, size, sort);
     */


}
