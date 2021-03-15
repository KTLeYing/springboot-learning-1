package com.example.springdemo3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName :   Teacher
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/18 14:38
 * @Version: 1.0
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "course_id")
    private int courseId;
    @Column(name = "teacher_name")
    private String teacherName;
}
