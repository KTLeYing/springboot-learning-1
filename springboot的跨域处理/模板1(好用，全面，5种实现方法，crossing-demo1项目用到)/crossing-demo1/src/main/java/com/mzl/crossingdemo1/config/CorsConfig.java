//package com.mzl.crossingdemo1.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * @ClassName :   CorsConfig
// * @Description: 后端跨域处理方法1：返回新的注册过滤器CorsFilter,直接在spring容器里作为原料并使用原料起作用了
// * @Author: mzl
// * @CreateDate: 2020/10/26 1:46
// * @Version: 1.0
// */
//@Configuration
//public class CorsConfig {
//
//    /**
//     * 跨域配置
//     * @return
//     */
//    private CorsConfiguration buildConfig(){
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
////        corsConfiguration.addAllowedOrigin("*");  //*表示所有的跨域请求
//        corsConfiguration.addAllowedOrigin("https://blog.csdn.net");  //局部跨域请求
//        corsConfiguration.addAllowedMethod("*");  //*表示所有
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.setMaxAge(3600L);
//        corsConfiguration.setAllowCredentials(true);  //用户请求访问的凭证
//        return corsConfiguration;
//    }
//
//    /**
//     * 返回一个起作用的跨域过滤器
//     * @return
//     */
//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); //使用上面的跨域配置来注册到这个跨域过滤器中，辅助过滤器起作用，/**表示拦截所有路径
//        return new CorsFilter(source);
//    }
//
//}
