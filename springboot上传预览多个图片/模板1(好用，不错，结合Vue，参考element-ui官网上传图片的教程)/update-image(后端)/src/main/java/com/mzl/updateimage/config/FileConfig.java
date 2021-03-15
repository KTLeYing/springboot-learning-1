package com.mzl.updateimage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName :   FileConfig
 * @Description: 文件配置类
 * @Author: mzl
 * @CreateDate: 2021/1/2 14:05
 * @Version: 1.0
 */
@Configuration
public class FileConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:/D:/ui/images/");
//                .addResourceLocations("file/ui/images/");
        super.addResourceHandlers(registry);
    }

}
