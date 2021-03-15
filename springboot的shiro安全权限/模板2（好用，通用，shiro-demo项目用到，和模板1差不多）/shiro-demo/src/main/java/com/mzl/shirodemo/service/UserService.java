package com.mzl.shirodemo.service;

import com.mzl.shirodemo.shiro.AccountProfile;
import org.springframework.stereotype.Service;

/**
 * @InterfaceName :   UserService
 * @Description: 用户业务逻辑接口
 * @Author: mzl
 * @CreateDate: 2020/9/16 19:47
 * @Version: 1.0
 */
public interface UserService {

    AccountProfile login(String username, String password);
}
