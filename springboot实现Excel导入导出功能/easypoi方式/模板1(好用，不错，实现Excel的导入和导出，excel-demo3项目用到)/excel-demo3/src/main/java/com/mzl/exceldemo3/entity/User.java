package com.mzl.exceldemo3.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2021/2/4 1:37
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Excel(name = "用户ID", width = 15, height = 10)    //excel表格的注解，用户excel导入导出的匹配
    private int id;
    @Excel(name = "用户名", width = 15, height = 10)
    private String username;
    @Excel(name = "密码", width = 15, height = 10)
    private String password;
    @Excel(name = "状态", width = 15, height = 10)
    private Integer status;

//    @Excel(name = "创建时间", exportFormat = "yyyy-MM-dd HH:mm:ss", importFormat ="yyyy-MM-dd HH:mm:ss",width = 25, orderNum = "3")
//    private LocalDateTime createTime;

//    @Excel(name = "密码", width = 15, orderNum = "4")
//    private String password;
//
//    @Excel(name = "性别", replace = {"男_true", "女_false"}, suffix = "生", width = 15, orderNum = "1")
//    private Boolean sex;
//
//    @Excel(name = "年龄")
//    private Integer age;
//
//    @Excel(name = "头像", width = 15, height = 15, type = 2, orderNum = "2")
//    private String headimg;
//
    /**连表查询时  需要深入导出关联对象属相*/
//    @ExcelEntity
//    private DepartMent departMent;

}
