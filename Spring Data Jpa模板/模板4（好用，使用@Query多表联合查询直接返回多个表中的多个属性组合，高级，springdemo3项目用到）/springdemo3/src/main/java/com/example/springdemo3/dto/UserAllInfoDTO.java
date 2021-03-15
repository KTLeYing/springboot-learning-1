package com.example.springdemo3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   UserAllInfoDTO
 * @Description: 通过自定义的dto数据传输对象返回到前端
 * @Author: mzl
 * @CreateDate: 2020/10/18 14:45
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAllInfoDTO {

    private int userId;
    private String username;
    private String phone;
    private String courseName;
    private String teacherName;
    private Double score;

    public UserAllInfoDTO(int userId, String username, String phone, String courseName, String teacherName) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }

    public UserAllInfoDTO(int userId, String username, String courseName, String teacherName, Double score) {
        this.userId = userId;
        this.username = username;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.score = score;
    }

    public UserAllInfoDTO(int userId, String username, String phone, String courseName) {
        this.userId = userId;
        this.username = username;
        this.phone = phone;
        this.courseName = courseName;
    }
}
