package com.mzl.md5logindemo1.service;

import com.mzl.md5logindemo1.common.Result;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @InterfaceName :   UserService
 * @Description: T用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/11/27 19:03
 * @Version: 1.0
 */
public interface UserService {

    /**
     * 用户登录验证
     * @param map
     * @param response
     * @return
     */
    Result login(Map<String, String> map, HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户注册
     * @param map
     * @return
     */
    Result register(Map<String, Object> map);

    /**
     * 用户激活
     * @param username
     * @return
     */
    Result active(String username);
}
