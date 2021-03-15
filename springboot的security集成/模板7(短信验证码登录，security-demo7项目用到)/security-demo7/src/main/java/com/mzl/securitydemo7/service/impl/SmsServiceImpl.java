package com.mzl.securitydemo7.service.impl;

import com.mzl.securitydemo7.exception.MyException;
import com.mzl.securitydemo7.result.CodeEnum;
import com.mzl.securitydemo7.result.Result;
import com.mzl.securitydemo7.service.SmsService;
import com.mzl.securitydemo7.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName :   SmsServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:47
 * @Version: 1.0
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private SmsUtils smsUtils;

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @Override
    public Result sendSms(String phone) {
        try {
            String smsCode = smsUtils.sendSms1(phone);  //调用发送方法1（CommonRequest）
//            smsUtils.sendSms2(phone);  //调用发送方法2（SendMsmRequest）
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg(), smsCode);
        } catch (MyException e) {
            e.printStackTrace();
            throw e;  //不自己处理的话，则抛给controller处理，这样统一异常处理类就会监听处理controller的异常
//            return new Result(e.getCode(), e.getMessage());  //自己处理了异常，不用抛上一层处理了，直接返回结果给controller，再返回道前端
        }catch (Exception e1){
            e1.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());  //自己处理了异常，不用抛上一层处理了
        }
    }
}
