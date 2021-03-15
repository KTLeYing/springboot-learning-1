package com.mzl.exceldemo4.entity;

import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 路径：com.example.demo.entity
 * 类名：
 * 功能：导出导入实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelData implements Serializable{

    //sheet名称
    private String sheetName;
    //文件名称
    private String fileName;
    //数据
    private List<?> data;

}
