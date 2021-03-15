package com.mzl.richtextarticle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   PageBean
 * @Description: 分页条件对象
 * @Author: mzl
 * @CreateDate: 2021/1/6 14:55
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean {

    private String articleType;  //文章类型
    private String userId;  //创建的用户id
    private Integer pageIndex;  //当前页数
    private Integer pageSize;  //每页大小


}
