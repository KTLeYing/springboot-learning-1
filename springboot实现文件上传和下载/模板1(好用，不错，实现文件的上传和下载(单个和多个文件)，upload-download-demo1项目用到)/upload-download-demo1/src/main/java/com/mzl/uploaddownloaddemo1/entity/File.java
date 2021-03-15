package com.mzl.uploaddownloaddemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   File
 * @Description: 文件实体类
 * @Author: mzl
 * @CreateDate: 2021/2/5 23:40
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {

    private Integer id;
    private String description;
    private String path;

}
