package com.mzl.rabbitmqdemo5.utils;

import com.mzl.rabbitmqdemo5.pojo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @ClassName :   MailUtils
 * @Description: 邮件工具类
 * @Author: mzl
 * @CreateDate: 2020/10/23 15:37
 * @Version: 1.0
 */
@Component
@Slf4j
public class MailUtils {

    @Value("${spring.mail.from}")
    private String from;

    @Autowired   //使用spring框架自带的javaMail来发送邮件
    private JavaMailSender javaMailSender;

    /**
     * 发送简单邮件
     *
     * @param mail
     */
    public boolean send(Mail mail){
        System.out.println("mail is:" + mail);
        System.out.println("hhh");
        String to = mail.getTo();  //接收邮件者
        String title = mail.getTitle();  //邮件标题
        String content = mail.getContent();  //邮件内容

        //创建简单的邮件消息对象
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(title);
        message.setText(content);
        System.out.println("message is: " + message);

        try{
            //发送邮件
            javaMailSender.send(message);
            log.info("发送邮件成功");
            return true;
        } catch (Exception e) {
            log.error("邮件发送失败, to: {}, title: {}", to, title, e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送附件邮件
     *
     * @param mail 邮件
     * @param file 附件
     */
    public boolean sendAttachment(Mail mail, File file){
        String to = mail.getTo();
        String title = mail.getTitle();
        String content = mail.getContent();

        //从邮件发送器获取发送附近的对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessage.setText(content);
            mimeMessage.setSubject(title);
            mimeMessage.setFrom(from);

            //设置附件文件
            FileSystemResource resource = new FileSystemResource(file);
            String fileName = file.getName();
            mimeMessageHelper.addAttachment(fileName, resource);
            //发送邮件附件
            javaMailSender.send(mimeMessage);
            log.info("附件邮件发送成功");
            return true;
        } catch (Exception e) {
            log.info("邮件附件发送失败，To:{}, From:{}", to, from, e);
            e.printStackTrace();
            return false;
        }

    }




}
