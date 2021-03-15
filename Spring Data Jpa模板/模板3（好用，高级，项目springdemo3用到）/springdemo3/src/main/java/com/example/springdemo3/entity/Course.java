package com.example.springdemo3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

/**
 * @ClassName :   Course
 * @Description: 课程
 * @Author: mzl
 * @CreateDate: 2020/10/18 14:38
 * @Version: 1.0
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "course_name")
    private String courseName;

}
