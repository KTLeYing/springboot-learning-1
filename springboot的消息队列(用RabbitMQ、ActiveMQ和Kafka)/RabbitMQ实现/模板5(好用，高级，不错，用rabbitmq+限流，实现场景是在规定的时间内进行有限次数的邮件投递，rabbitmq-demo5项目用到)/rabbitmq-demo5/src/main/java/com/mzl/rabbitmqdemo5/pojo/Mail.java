package com.mzl.rabbitmqdemo5.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * @ClassName :   Mail
 * @Description: 邮件实体类
 * @Author: mzl
 * @CreateDate: 2020/10/22 9:08
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mail {

    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$", message = "邮箱格式不正确")
    private String to;

    @NotBlank(message = "标题不能为空")  //只能判断字符串，不能是空
    private String title;

    @NotBlank(message = "正文不能为空")
    private String content;

    //消息id，唯一标识
    private String msgId;


}
