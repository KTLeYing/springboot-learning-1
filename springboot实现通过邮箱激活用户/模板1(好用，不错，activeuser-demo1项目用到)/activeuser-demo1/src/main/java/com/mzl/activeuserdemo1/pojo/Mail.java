package com.mzl.activeuserdemo1.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @ClassName :   Mail
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/24 19:58
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail implements Serializable {

    private String[] tos;  //接收者，可以设置多个

    private String subject; //邮件主题

    private String content;   //邮件内容


}
