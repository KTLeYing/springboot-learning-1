package com.mzl.qiniuyundemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   File
 * @Description: 文件上传实体类
 * @Author: mzl
 * @CreateDate: 2021/2/17 12:30
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyFile {

    private Integer id;
    private String description;
    private String path;

}
