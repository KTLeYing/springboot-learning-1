package com.example.securitydemo1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName :   WebSecurityConfig
 * @Description: 安全页面配置
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:10
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//@EnableGlobalMethodSecurity(securedEnabled = true)  //或
//@EnableGlobalMethodSecurity(prePostEnabled= true)  //或
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**该类是 Spring Security 的配置类，该类的三个注解分别是标识该类是配置类、开启 Security 服务、开启全局 Securtiy 注解。
     *首先将我们自定义的 userDetailsService 注入进来，在 configure() 方法中使用 auth.userDetailsService() 方法替换掉默认的 userDetailsService。
     *这里我们还指定了密码的加密方式（5.0 版本强制要求设置），因为我们数据库是明文存储的，所以明文返回即可，如下所示：
     */
    //注入依赖，使用原料customUserDetailsService，使起作用
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     *  配置认证用户信息和权限
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    /** 密码是明文，未加密 */
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(
            new PasswordEncoder() {

                /**
                 *前端登录页面表单的密码的name一定是“password”， encode(CharSequence charSequence)的charSequence参数
                 * 自动识别password匹配
                 * @param charSequence
                 * @param
                 * @return
                 */
              @Override
              public String encode(CharSequence charSequence) {
                System.out.println("jjj");
                System.out.println(charSequence.toString());
                return charSequence.toString();
              }

                /**
                 *前端登录页面表单的密码的name一定是“password”， matches(CharSequence charSequence, String s)的charSequence参数
                 * 自动识别password匹配
                 * @param charSequence
                 * @param s
                 * @return
                 */
              @Override
              public boolean matches(CharSequence charSequence, String s) {
                System.out.println("iii");
                System.out.println(charSequence.toString());
                System.out.println(s);
                return s.equals(charSequence.toString());
              }
            });

        /*** 密码加密的*/
//        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder() {
//            @Override
//            public String encode(CharSequence charSequence) {
//                System.out.println("jjj");
//                return charSequence.toString();
//            }
//
//            @Override
//            public boolean matches(CharSequence charSequence, String s) {
//                System.out.println("iii");
//                return s.equals(charSequence.toString());
//            }
//        });
    }

    /**
     * 配置拦截http请求资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        // 如果有允许匿名的url，填在下面
//                .antMatchers().permitAll()
                .anyRequest()
                //没有权限，系统会默认跳转到template目录下的/error页面，如果没创建error页面，系统则会自动产生，并报403权限详细错误信息
                .authenticated()
                .and()
                // 设置登陆页，启动程序后，不管访问什么，都会要先来到登录页面来登录
                .formLogin().loginPage("/login")
                // 设置登陆成功页,登录成功可以访问所有的页面
                .defaultSuccessUrl("/home").permitAll()
                // 自定义登陆用户名和密码参数，默认为username和password
//                .usernameParameter("username")
//                .passwordParameter("password")
                .and()
                .logout().permitAll();

        // 关闭CSRF跨域
        http.csrf().disable();
    }

    /**
     * 页面配置方法
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行,多个值用逗号隔开
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

}
