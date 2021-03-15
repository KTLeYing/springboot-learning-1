package com.mzl.rabbitmqdemo6.service;

import com.mzl.rabbitmqdemo6.common.ServerResponse;
import com.mzl.rabbitmqdemo6.pojo.Mail;

/**
 * @InterfaceName :   TestService
 * @Description: 测试业务逻辑
 * @Author: mzl
 * @CreateDate: 2020/11/27 11:53
 * @Version: 1.0
 */
public interface TestService {

    /**
     * 投递发送邮件
     * @param mail
     * @return
     */
    ServerResponse send(Mail mail);

}
