package com.mzl.activemqdemo2.service;

import com.mzl.activemqdemo2.result.Result;

/**
 * @InterfaceName :   SmsService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:48
 * @Version: 1.0
 */
public interface SmsService {

    /**
     * 发送短信
     * @param phone
     * @return
     */
    Result sendSms(String phone);
}
