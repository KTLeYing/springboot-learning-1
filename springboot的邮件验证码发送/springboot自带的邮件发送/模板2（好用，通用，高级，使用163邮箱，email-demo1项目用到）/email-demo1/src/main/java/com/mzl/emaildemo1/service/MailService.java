package com.mzl.emaildemo1.service;

import com.mzl.emaildemo1.pojo.Mail;
import com.mzl.emaildemo1.result.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @InterfaceName :   MailService
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:50
 * @Version: 1.0
 */
public interface MailService {

    /**
     * 发送普通文本邮件
     * @param mail
     * @return
     */
    Result sendCommonEmail(Mail mail);

    /**
     * 发送html邮件
     * @param mail
     * @return
     */
    Result sendHtmlEmail(Mail mail);

    /**
     * 发送带附件邮件
     * @param mail
     * @param multipartFile
     * @return
     */
    Result sendAttachedEmail(Mail mail, MultipartFile multipartFile);

    /**
     * 发送静态资源邮件
     * @param mail
     * @param multipartFile
     * @param resId
     * @return
     */
    Result sendStaticEmail(Mail mail, MultipartFile multipartFile, String resId);

    /**
     * 发送带附件邮件1
     * @param mail
     * @param
     * @return
     */
    Result sendAttachedEmail1(Mail mail, String file1, String file2);

    /**
     * 发送静态资源邮件1
     * @return
     */
    Result sendStaticEmail1(Mail mail, String[] imagePaths, String[] imageId);
}
