package com.mzl.shirojwt.shiro.filter;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName :   ReleaseAddressUtil
 * @Description: 放行地址的工具类(自定义要放行的接口)
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:31
 * @Version: 1.0
 */
public class ReleaseAddressUtil {

    /**
     * 设置放行的接口路径在集合里面,这些接口可以直接访问，不需要被拦截认证
     * @return
     */
    private static Set<String> getInterface() {
        Set<String> set =new HashSet<String>();
        set.add("/user/login");
        set.add("/user/register");
        return set;
    }

    /**
     * 验证请求的路径是否在放行的路径设置里，验证是否需要放行
     * @param requestURI
     * @return
     */
    public static Boolean confirm(String requestURI) {
        //调用方法获取放行的请求的接口路径
        Set<String> set = getInterface();
        return set.contains(requestURI);
    }

}
