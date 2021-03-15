package com.mzl.apidesigndemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName :   AppInfo（user和api都可以使用）
 * @Description: app的信息实体类
 * @Author: mzl
 * @CreateDate: 2020/12/19 16:45
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppInfo {

    //App id
    private String appId;

    //API密匙
    private String key;
}
