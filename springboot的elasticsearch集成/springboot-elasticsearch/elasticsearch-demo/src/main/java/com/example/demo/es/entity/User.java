package com.example.demo.es.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 路径：com.example.demo.es.entity
 * 类名：
 * 功能：实体类
 * 备注：indexName索引名称：可以理解为数据库名。必须为小写，不然会报org.elasticsearch.indices.InvalidIndexNameException异常
         type类型：可以理解为表名
 * 创建人：typ
 * 创建时间：2019/1/8 9:41
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Data
@Document(indexName = "sys", type = "user")
public class User implements Serializable {

    private Long id;

    private String name;

    private String description;
}
