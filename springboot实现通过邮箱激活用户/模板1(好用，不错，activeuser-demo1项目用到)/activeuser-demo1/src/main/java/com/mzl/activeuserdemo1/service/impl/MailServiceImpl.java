package com.mzl.activeuserdemo1.service.impl;

import com.mzl.activeuserdemo1.pojo.Mail;
import com.mzl.activeuserdemo1.result.CodeEnum;
import com.mzl.activeuserdemo1.result.Result;
import com.mzl.activeuserdemo1.service.MailService;
import com.mzl.activeuserdemo1.utils.MailUtils;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName :   MailServiceImpl
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:50
 * @Version: 1.0
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailUtils mailUtils;

    /**
     * 发送普通邮件文本
     * @param mail
     */
    @Override
    public Result sendCommonEmail(Mail mail) {
        try{
            mailUtils.sendCommonEmail(mail);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }

    /**
     * 发送html邮件
     * @param mail
     * @return
     */
    @Override
    public Result sendHtmlEmail(Mail mail) {
        try{
            mailUtils.sendHtmlEmail(mail);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }

    /**
     * 发送带附件邮件
     * @param mail
     * @param multipartFile
     * @return
     */
    @Override
    public Result sendAttachedEmail(Mail mail, MultipartFile multipartFile) {
        try{
            mailUtils.sendAttachedEmail(mail, multipartFile);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }

    /**
     * 发送带附件邮件1
     * @param mail
     * @param
     * @return
     */
    @Override
    public Result sendAttachedEmail1(Mail mail, String file1, String file2) {
        try{
            mailUtils.sendAttachedEmail1(mail, file1, file2);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }

    /**
     * 发送静态资源邮件
     * @param mail
     * @param multipartFile
     * @param resId
     * @return
     */
    @Override
    public Result sendStaticEmail(Mail mail, MultipartFile multipartFile, String resId) {
        try{
            mailUtils.sendStaticEmail(mail, multipartFile, resId);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }

    /**
     * 发送静态资源邮件1
     * @return
     */
    @Override
    public Result sendStaticEmail1(Mail mail, String[] imagePaths, String[] imageId) {
        try{
            mailUtils.sendStaticEmail1(mail, imagePaths, imageId);
            return new Result(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(CodeEnum.FAIL.getCode(), CodeEnum.FAIL.getMsg());
        }
    }


}
