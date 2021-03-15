package com.mzl.securitydemo7.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName :   SmsBean
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/10/25 10:33
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "aliyun.sms") //获取配置文件的属性值（用prefix来统一获取，后缀是根据属性名来同名映射赋值）
@Component   //注册组件到容器后，这个实体类不能有@AllArgsConstructor和@NoArgsConstructor注解了，最终变成了一个配置类
public class SmsBean {

    /**
     * 阿里云AK
     */
    private String accessKeyId;
    /**
     * 阿里云SK
     */
    private String accessKeySecret;
    /**
     * 阿里云短信末班ID
     */
    private String templateCode;
    /**
     * 阿里云短信签名
     */
    private String signName;

    /**
     * 产品域名,开发者无需替换
     */
    private String domain;

    /**
     * 产品名称:云通信短信API产品,开发者无需替换
     */
    private String product;

}
