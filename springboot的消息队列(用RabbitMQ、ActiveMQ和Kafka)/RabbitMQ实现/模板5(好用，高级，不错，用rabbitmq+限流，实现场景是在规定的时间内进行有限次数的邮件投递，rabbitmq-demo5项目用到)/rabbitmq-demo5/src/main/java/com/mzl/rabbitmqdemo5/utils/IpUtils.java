package com.mzl.rabbitmqdemo5.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   IpUtils
 * @Description: ip工具类
 * @Author: mzl
 * @CreateDate: 2020/10/23 9:40
 * @Version: 1.0
 */
public class IpUtils {

    /**
     * 获取客户端真实ip地址
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")){
            ip = request.getRemoteAddr();
        }

        System.out.println(ip);
        return ip;
    }

}
