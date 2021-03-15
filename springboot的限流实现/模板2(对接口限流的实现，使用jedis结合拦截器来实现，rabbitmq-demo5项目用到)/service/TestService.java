package com.mzl.rabbitmqdemo5.service;

import com.mzl.rabbitmqdemo5.common.ServerResponse;
import com.mzl.rabbitmqdemo5.pojo.Mail;

/**
 * @InterfaceName :   TestService
 * @Description: 测试的业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/10/23 12:14
 * @Version: 1.0
 */
public interface TestService {


    /**
     * 限流测试接口
     * @return
     */
    ServerResponse accessLimit();


    /**
     * 幂等性测试接口
     * @return
     */
    ServerResponse testIdempotence();
    
}
