package com.mzl.SSOdemo1.service;

/**
 * @InterfaceName :   AuthTokenService
 * @Description: 认证token的业务逻辑
 * @Author: mzl
 * @CreateDate: 2021/1/30 10:30
 * @Version: 1.0
 */
public interface AuthTokenService {

    /**
     * 获取用户token
     * @param userId
     * @return
     * @throws Exception
     */
    String getToken(String userId) throws Exception;

    /**
     * 校验用户的token
     * @return
     * @throws Exception
     */
    String checkToken(String token) throws Exception;
}
