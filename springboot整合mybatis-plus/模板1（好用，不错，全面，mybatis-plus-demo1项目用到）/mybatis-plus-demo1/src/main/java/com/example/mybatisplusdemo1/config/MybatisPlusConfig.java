package com.example.mybatisplusdemo1.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName :   MybatisPlusConfig
 * @Description: mybatis-plus分页插件配置
 * @Author: mzl
 * @CreateDate: 2020/11/6 10:19
 * @Version: 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.example.mybatisplusdemo1.mapper")
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
