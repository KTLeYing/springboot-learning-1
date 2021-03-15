package com.mzl.exceldemo4.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
@ContentRowHeight(20)  //声明格子的高度
//@HeadRowHeight(40)   //声明表格头高度
public class User extends BaseRowModel{

    /**
     * 通过 @ExcelProperty 的value 指定每个字段的列名称，index 为列的序号。
     */
    @ExcelProperty(value = "用户ID", index = 0)    //excel表格的注解，用于excel导入导出的匹配
    private int id;
    @ExcelProperty(value = "用户名", index = 1)
    private String username;
    @ExcelProperty(value = "密码", index = 2)
    private String password;
    @ExcelProperty(value = "状态", index = 3)
    private Integer status;

//    @ExcelIgnore
//    @ExcelProperty("ID")
//    private String id;
//
//    @ColumnWidth(20)
//    @ExcelProperty(value = "学生姓名",index = 0)
//    private String name;
//
//    @ExcelProperty(value = "学生性别",index = 1)
//    private String gender;
//
//    @ColumnWidth(20)
//    @DateTimeFormat("yyyy-MM-dd")
//    @ExcelProperty(value = "出生日期",index = 2)
//    private Date date;


}
