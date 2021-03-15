package com.mzl.avoidpostdemo2.service;

import org.springframework.stereotype.Service;

/**
 * @ClassName :   TestService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/22 21:57
 * @Version: 1.0
 */
@Service
public class TestService {

    public String testMethod(){
        return "正常的业务逻辑";
    }
}
