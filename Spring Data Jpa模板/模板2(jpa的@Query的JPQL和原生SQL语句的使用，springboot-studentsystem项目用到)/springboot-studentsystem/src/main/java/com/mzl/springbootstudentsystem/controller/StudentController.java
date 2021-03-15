package com.mzl.springbootstudentsystem.controller;

import com.mzl.springbootstudentsystem.entity.PageBean;
import com.mzl.springbootstudentsystem.entity.Student;
import com.mzl.springbootstudentsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName :   StudentController
 * @Description: 学生控制器
 * @Author: 21989
 * @CreateDate: 2020/7/26 11:20
 * @Version: 1.0
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    //注入依赖
    @Autowired
    private StudentService studentService;

    /**
     * 学生登录
     * @param student
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Student login(Student student){
        System.out.println(student);
        return studentService.login(student);
    }

    /**
     * 查询所有学生
     * @return
     */
    @GetMapping("/findAllStu")
    @ResponseBody
    public List<Student> findAllStu(){
        return studentService.findAllStu();
    }

    /**
     * 通过用户id来查询学生信息(法1)
     * 请求的路径： http://localhost:8080/student/findById/1  （和@RequestParameter不同）
     * @PathVariable("xxx")
     * 通过 @PathVariable 可以将URL中占位符参数{xxx}绑定到处理器类的方法形参中@PathVariable(“xxx“)
     * @RequestMapping(value="/user/show5/{id}/{name}")
     * 请求路径：http://localhost:8080/user/show5/1/james
     * @param id
     * @return
     */
    @GetMapping("/findById/{id}")
    @ResponseBody
    public Student findById(@PathVariable("id") int id){
        return studentService.findById(id);
    }

    /**
     * 通过用户id来查询学生信息（法2）
     * 请求的路径： http://localhost:8080/student/findById1?id=1
     * @RequestParam：将请求参数绑定到你控制器的方法参数上（是springmvc中接收普通参数的注解）
     * 语法：@RequestParam(value=”参数名”,required=”true/false”,defaultValue=””)
     * value：参数名
     * required：是否包含该参数，默认为true，表示该请求路径中必须包含该参数，如果不包含就报错。
     * defaultValue：默认参数值，如果设置了该值，required=true将失效，自动为false,如果没有传该参数，就使用默认值
     * @param id
     * @return
     */
    @GetMapping("/findById1")
    @ResponseBody
    public Student findById1(@RequestParam("id") int id){
        return studentService.findById(id);
    }

    /**
     * 通过用户id来查询学生信息（法3）
     * 请求的路径： http://localhost:8080/student/findById2?id=1
     * 和含有@RequestParam注解一样的（但是与@PathVariable不一样）
     * @param id
     * @return
     */
    @GetMapping("/findById2")
    @ResponseBody
    public Student findById2(int id){
        return studentService.findById(id);
    }

    /**
     * 通过账号来查询学生
     * @param account
     * @return
     */
    @GetMapping("/findByAccount")
    @ResponseBody
    public Student findByAccount(String account){
        return studentService.findByAccount(account);
    }

    /**
     * 通过姓名查询学生
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    @ResponseBody
    public Student findByName(String name){
        return studentService.findByName(name);
    }

    /**
     * 学生注册（添加学生）
     * @param student
     * @return
     */
    @PostMapping("/addStu")
    @ResponseBody
    public Student addStudent(Student student){
        System.out.println(student);
        studentService.addStudent(student);
        return student;
    }

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    @PostMapping("/updateStu")
    @ResponseBody
    public String updateStudent(Student student){
        System.out.println(student);
        if (studentService.findById(student.getId()) != null){
            studentService.updateStudent(student);
            return "SUCCESS";
        }
        return "更新失败！此用户不存在！";
    }

    /**
     * 用id删除学生
     * @param id
     * @return
     */
    @GetMapping("/deleteStu")
    @ResponseBody
    public String deleteStudent(int id){
        studentService.deleteStudent(id);
        return "SUCCESS";
    }

    /**
     * 批量删除学生（删除选中）
     * @param ids
     * @return
     */
    @PostMapping("/deleteManyStu")
    @ResponseBody
    public String deleteManyStu(String ids){
        System.out.println(ids);
        String[] arr = ids.split(",");
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (String id : arr) {
            System.out.println(id);
            arrayList.add(Integer.valueOf(id));   //字符串转换为整型
        }
        studentService.deleteManyStu(arrayList);
        return "SUCCESS";
    }

    /**
     * 模糊查询学生
     * @param student
     * @return
     */
    @PostMapping("/findStuByCondition")
    @ResponseBody
    public List<Student> findStuByCondition(Student student){
        System.out.println(student);
        return studentService.findStuByCondition(student);
    }

    /**
     * 分页查询学生（使用jpa的分页功能）
     * @param curPage
     * @param pageSize
     * @return
     */
    @PostMapping("/findStuByPage")
    @ResponseBody
    public PageBean<Student> findAllStuByPage(Integer curPage, Integer pageSize, Student student){
        return studentService.findAllStuByPage(curPage, pageSize, student);
    }

    /**
     * 分页查询（使用jpa自带的pageable分页功能）
     * @param curPage
     * @param response
     * @return
     */
    @GetMapping("/findByPage")
    @ResponseBody
    public Page<Student> findByPage(Integer curPage, HttpServletResponse response){
        //解决跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*");

        if (curPage == null || curPage <= 0){
            curPage = 0;
        }else {
            curPage = curPage - 1;
        }

        return studentService.findByPage(curPage, 3);

    }



}
