package com.mzl.shirojwt.config;

import org.springframework.stereotype.Component;

/**
 * @ClassName :   Config
 * @Description: 通用的配置
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:23
 * @Version: 1.0
 */
@Component
public class Config {

    //是否拦截请求
    public static Boolean shiroConfig = false;

    //是否开放游客权限(false表示一定需要的登录，没有开启游客权限)
    public static Boolean mustLoginFlag = false;

}
