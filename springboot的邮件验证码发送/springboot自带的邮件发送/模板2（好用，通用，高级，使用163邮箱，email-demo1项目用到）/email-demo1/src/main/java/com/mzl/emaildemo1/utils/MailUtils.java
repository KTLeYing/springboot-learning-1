package com.mzl.emaildemo1.utils;

import com.mzl.emaildemo1.pojo.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   MailUtils
 * @Description: 邮件工具类
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:47
 * @Version: 1.0
 */
@Slf4j
@Component
public class MailUtils {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;   //发送者

    /**
     * 发送普通的邮件
     */
    public void sendCommonEmail(Mail mail) throws Exception{
        //创建简单额邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //设置发送者
        message.setFrom(from);
        //设置接收者(一个或多个)
        message.setTo(mail.getTos());
        //设置邮件主题
        message.setSubject(mail.getSubject());
        //设置邮件内容
        message.setText(mail.getContent());

        try{
            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 发送Html的邮件
     */
    public void sendHtmlEmail(Mail mail) throws Exception{
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
        //设置发送者
        messageHelper.setFrom(from);
        //设置接收者(一个或多个)
        messageHelper.setTo(mail.getTos());
        //设置邮件主题
        messageHelper.setSubject(mail.getSubject());
        //邮件内容   true 表示带有附件或html,默认是false
        messageHelper.setText(mail.getContent(), true);

        try{
            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 带附件发送的邮件
     */
    public void sendAttachedEmail(Mail mail, MultipartFile multipartFile) throws Exception{
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //设置发送者
            messageHelper.setFrom(from);
            //设置接收者(一个或多个)
            messageHelper.setTo(mail.getTos());
            //设置邮件主题
            messageHelper.setSubject(mail.getSubject());
            //邮件内容   true 表示带有附件或html,默认是false
            messageHelper.setText(mail.getContent(), true);

            //MultipartFileToFile转换为File
            File multipartFileToFile = MultipartFileToFile(multipartFile);
            FileSystemResource file = new FileSystemResource(multipartFileToFile);
            //获取文件名
            String fileName = file.getFilename();
            //添加附件
            messageHelper.addAttachment(fileName, file);

            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 带附件发送的邮件1
     */
    public void sendAttachedEmail1(Mail mail, String file1, String file2) throws Exception{
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(file1));
        fileList.add(new File(file2));

        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();
        try{
            /*
            第二个参数true表示构造一个multipart message类型的邮件，multipart message类型的邮件包含多个正文、附件以及内嵌资源，
            邮件的表现形式更丰富
             */
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //设置发送者
            messageHelper.setFrom(from);
            //设置接收者(一个或多个)
            messageHelper.setTo(mail.getTos());
            //设置邮件主题
            messageHelper.setSubject(mail.getSubject());
            //邮件内容   true 表示带有附件或html,默认是false
            messageHelper.setText(mail.getContent(), true);
            //添加附件
            if (fileList != null){
                for (File file : fileList) {
                    //添加附件
                    messageHelper.addAttachment(file.getName(), file);
                }
            }

            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 发送静态资源
     * @param mail
     * @param multipartFile
     * @throws Exception
     */
    public void sendStaticEmail(Mail mail, MultipartFile multipartFile, String resId) throws Exception{
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //设置发送者
            messageHelper.setFrom(from);
            //设置接收者(一个或多个)
            messageHelper.setTo(mail.getTos());
            //设置邮件主题
            messageHelper.setSubject(mail.getSubject());

            //邮件内容   true 表示带有附件或html,默认是false
            //邮件内容拼接
            String content = "<html><body><img width='250px' src=\'cid:" + resId + "\'>" + mail.getContent() + "</body></html>";
            messageHelper.setText(content, true);

            //MultipartFileToFile转换为File
            File multipartFileToFile = MultipartFileToFile(multipartFile);
            FileSystemResource res = new FileSystemResource(multipartFileToFile);

            //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
            messageHelper.addInline(resId, res);

            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 发送静态资源邮件1
     * @param mail
     * @param imagePaths
     * @param imageId
     */
    public void sendStaticEmail1(Mail mail, String[] imagePaths, String[] imageId) throws Exception {
        //创建一个MINE消息
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //设置发送者
            messageHelper.setFrom(from);
            //设置接收者(一个或多个)
            messageHelper.setTo(mail.getTos());
            //设置邮件主题
            messageHelper.setSubject(mail.getSubject());
            //邮件内容   true 表示带有附件或html,默认是false
            messageHelper.setText(mail.getContent(), true);

            // 添加图片
            if (imagePaths != null && imagePaths.length != 0){
                for (int i = 0; i < imagePaths.length; i++){
                    // 通过FileSystemResource构造静态资源
                    FileSystemResource fileSystemResource = new FileSystemResource(imagePaths[i]);
                    //添加内联资源，一个id对应一个资源，最终通过id来找到该资源
                    messageHelper.addInline(imageId[i], fileSystemResource);
                }
            }

            //发送邮件
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);   //抛出异常给上一层来处理(调用者)
        }
    }

    /**
     * 将 multpartfile 转为file
     * @param multipartFile
     * @return
     */
    private File MultipartFileToFile(MultipartFile multipartFile) {
        //获取文件名
        String fileName = multipartFile.getOriginalFilename();
        System.out.println(fileName);
        //获取文件后缀名
        String prefix = fileName.substring(fileName.indexOf("."));
        System.out.println(prefix);

        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码
        try{
            File file = File.createTempFile(fileName, prefix);
            multipartFile.transferTo(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
