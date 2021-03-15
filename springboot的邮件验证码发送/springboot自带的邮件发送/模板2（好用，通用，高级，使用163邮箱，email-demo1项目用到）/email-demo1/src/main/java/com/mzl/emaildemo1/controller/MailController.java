package com.mzl.emaildemo1.controller;

import com.mzl.emaildemo1.pojo.Mail;
import com.mzl.emaildemo1.result.CodeEnum;
import com.mzl.emaildemo1.result.Result;
import com.mzl.emaildemo1.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName :   MailController
 * @Description: 邮件测试控制器
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:47
 * @Version: 1.0
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    /**
     * 发送普通邮件文本
     * @return
     */
    @RequestMapping("/sendCommonEmail")
    public Result sendCommonEmail(Mail mail){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        return mailService.sendCommonEmail(mail);
    }

    /**
     * 发送html邮件文本
     * @return
     */
    @RequestMapping("/sendHtmlEmail")
    public Result sendHtmlEmail(Mail mail){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格,html格式的内容直接传就行，比如：<h1>...</h>
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        return mailService.sendHtmlEmail(mail);
    }

    /**
     * 发送带附件邮件
     * @return
     */
    @RequestMapping("/sendAttachedEmail")
    public Result sendAttachedEmail(Mail mail, @RequestParam("multipartFile") MultipartFile multipartFile){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格,html格式的内容直接传就行，比如：<h1>...</h>
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        System.out.println(multipartFile);
        return mailService.sendAttachedEmail(mail, multipartFile);
    }

    /**
     * 发送带附件邮件1
     * @return
     */
    @RequestMapping("/sendAttachedEmail1")
    public Result sendAttachedEmail1(Mail mail, String file1, String file2){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格,html格式的内容直接传就行，比如：<h1>...</h>
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        return mailService.sendAttachedEmail1(mail, file1, file2);
    }

    /**
     * 发送静态资源邮件
     * @return
     */
    @RequestMapping("/sendStaticEmail")
    public Result sendStaticEmail(Mail mail, MultipartFile multipartFile, String resId){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格,html格式的内容直接传就行，比如：<h1>...</h>
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        System.out.println(multipartFile);
        return mailService.sendStaticEmail(mail, multipartFile, resId);
    }

    /**
     * 发送静态资源邮件1
     * @return
     */
    @RequestMapping("/sendStaticEmail1")
    public Result sendStaticEmail1(Mail mail, String[] imagePaths, String[] imageId){
        //mail参数数组参数tos[]前端传参时多个数组的值用逗号隔开就行，逗号后面不用有空格,html格式的内容直接传就行，比如：<h1>...</h>
        // imagePaths请求格式:src\\main\\java\\com\\mzl\\emaildemo1\\file\\a.jpg,src\\main\\java\\com\\mzl\\emaildemo1\\file\\b.jpg（数组，多个值用逗号隔开）
        // imageId请求格式：1,2
        System.out.println(mail);
        System.out.println(mail.getTos()[0]);
        System.out.println(mail.getTos()[1]);
        return mailService.sendStaticEmail1(mail, imagePaths, imageId);
    }

}
