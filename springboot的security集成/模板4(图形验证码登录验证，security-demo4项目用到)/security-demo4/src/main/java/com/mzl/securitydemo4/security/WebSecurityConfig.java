package com.mzl.securitydemo4.security;

import com.mzl.securitydemo4.filter.VerifyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

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
    //然后我们将 CustomAuthenticationDetailsSource 注入Spring Security中，替换掉默认的 AuthenticationDetailsSource。
    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    /**
     *  配置认证用户信息和权限
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /**密码是明文，未加密*/
//
//        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
//              @Override
//              public String encode(CharSequence charSequence) {
//                 System.out.println("jjj");
//                 return charSequence.toString();
//              }
//
//              @Override
//              public boolean matches(CharSequence charSequence, String s) {
//                  System.out.println("iii");
//                  return s.equals(charSequence.toString());
//              }
//            });
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

        // 指定 provider
        auth.authenticationProvider(customAuthenticationProvider);
    }

    /**
     * 配置拦截http请求资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // 如果有允许匿名的url，填在下面，设置允许多个路径，则用逗号隔开
        .antMatchers("/getVerifyCode")
        .permitAll()
        .anyRequest()
        // 没有权限，系统会默认跳转到template目录下的/error页面，如果没创建error页面，系统则会自动产生，并报403权限详细错误信息
        .authenticated()
        .and()
        // 设置登陆页，启动程序后，不管访问什么，都会要先来到登录页面来登录
        .formLogin()
        .loginPage("/login")
        // 设置登陆成功页,登录成功可以访问所有的页面
        .defaultSuccessUrl("/home")
        .permitAll()
        // 设置登录异常页面
        .failureUrl("/loginError")
        // 自定义登陆用户名和密码参数，默认为username和password
        //                .usernameParameter("username")
        //                .passwordParameter("password")
         // 指定authenticationDetailsSource, 替换WebAuthenticationDetails，存前端的其他参数
        .authenticationDetailsSource(authenticationDetailsSource)
        .and()
        // 修改 WebSecurityConfig 的 configure 方法，添加一个 addFilterBefore() ，具有两个参数，作用是在参数二之前执行参数一设置的过滤器。
        // Spring Security 对于用户名/密码登录方式是通过 UsernamePasswordAuthenticationFilter
        // 处理的，我们在它之前执行验证码过滤器即可。
//        .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
        .logout()
        .permitAll();

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
