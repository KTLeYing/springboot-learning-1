package com.jobgo.gateway.config;

import com.jobgo.gateway.interceptor.CsrfInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @ClassName :   CsrfInterceptorConfig
 * @Description: 创建csrf拦截器配置类，设置拦截静态资源,告诉拦截的要拦截那些路径
 * @Author: mzl
 * @CreateDate: 2020/9/22 10:57
 * @Version: 1.0
 */
@Configuration   //配置注解告诉springboot在加载配置时加载此类
public class CsrfInterceptorConfig extends WebMvcConfigurerAdapter {   //继承了WebMvcConfigurerAdapter

    @Bean   //将自定义拦截器CsrfInterceptor注册到spring bean中
    public CsrfInterceptor csrfInterceptor(){
        return new CsrfInterceptor();
    }

    /**
     *  注册添加拦截器，配置拦截地址
     * 重写WebMvcConfigurerAdapter中的addInterceptors方法
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(csrfInterceptor()) //new一个自定义的拦截器类对象
                .addPathPatterns("/**")  // /**代表拦截所有请求，包括多级路径（/*只是包括后一级路径，不包括多级路径），动态参数，多个参数以逗号隔开
                .excludePathPatterns("/login","/login"); //不拦截这些路径请求，这也是个动态参数，有多个不拦截的请求时，以逗号隔开
        super.addInterceptors(registry);
    }

  /**
   * 注册提交资源处理器
   * 继承WebMvcConfigurationSupport类会导致自动配置失效 * 所以这里要指定默认的静态资源的位置
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/*")
              .addResourceLocations("classpath:/resources/")  //classpath要带上，因为boot项目默认的
              .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

}
