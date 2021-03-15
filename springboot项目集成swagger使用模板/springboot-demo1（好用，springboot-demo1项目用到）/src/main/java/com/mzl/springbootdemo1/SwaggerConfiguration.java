package com.mzl.springbootdemo1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName :   SwaggerConfiguration
 * @Description: TODO
 * @Author: 21989
 * @CreateDate: 2020/7/24 21:21
 * @Version: 1.0
 */
@Configuration  //标记配置类
@EnableSwagger2  //开启在线接口文档
//@EnableWebMvc  //开启这个注解要放行路径，不然访问swagger的页面时会被拦截，出现404
public class SwaggerConfiguration{

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mzl.springbootdemo1.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger接口文档")
                .description("Swagger测试")
                .termsOfServiceUrl("http://localhost:8080/swagger-ui.html")
                .version("2.0")
                .build();
    }

/*
    @Configuration声明为配置类，@EnableSwagger2则开启Swagger2文档支持，其余大致为固定写法，只是具体属性有所不同。
    basePackage指明文档包含的包范围（主要配置点），title和description即为文档标题和描述，contact为创建人信息，version是版本信息。
*/

}
