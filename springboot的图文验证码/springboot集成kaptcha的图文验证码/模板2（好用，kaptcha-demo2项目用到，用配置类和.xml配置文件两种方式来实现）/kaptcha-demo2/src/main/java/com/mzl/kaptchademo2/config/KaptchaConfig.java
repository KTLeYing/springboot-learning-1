package com.mzl.kaptchademo2.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @ClassName :   KaptchaConfig
 * @Description: 验证码配置类(可以使用.xml类配置)
 * @Author: mzl
 * @CreateDate: 2020/10/25 23:46
 * @Version: 1.0
 */
//@Component  //或@Component和@Configuration作用差不多的，都是把bean注入到spring容器，作为原料
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getKaptchaBean(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.string", "123456789ABCEFGHJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }


}
