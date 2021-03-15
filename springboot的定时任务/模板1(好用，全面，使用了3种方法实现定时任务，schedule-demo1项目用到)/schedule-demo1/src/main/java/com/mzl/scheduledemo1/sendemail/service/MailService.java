package com.mzl.scheduledemo1.sendemail.service;

import com.mzl.scheduledemo1.sendemail.pojo.Mail;
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
    void sendCommonEmail(Mail mail);

    /**
     * 发送html邮件
     * @param mail
     * @return
     */
    void sendHtmlEmail(Mail mail);

    /**
     * 发送带附件邮件
     * @param mail
     * @param multipartFile
     * @return
     */
    void sendAttachedEmail(Mail mail, MultipartFile multipartFile);

    /**
     * 发送静态资源邮件
     * @param mail
     * @param multipartFile
     * @param resId
     * @return
     */
    void sendStaticEmail(Mail mail, MultipartFile multipartFile, String resId);

    /**
     * 发送带附件邮件1
     * @param mail
     * @param
     * @return
     */
    void sendAttachedEmail1(Mail mail, String file1, String file2);

    /**
     * 发送静态资源邮件1
     * @return
     */
    void sendStaticEmail1(Mail mail, String[] imagePaths, String[] imageId);
}
