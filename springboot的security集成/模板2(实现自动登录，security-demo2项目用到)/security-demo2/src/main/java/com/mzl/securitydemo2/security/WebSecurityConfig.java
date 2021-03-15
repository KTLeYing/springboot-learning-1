package com.mzl.securitydemo2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

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
    //注入数据源，拿来创建一个 PersistentTokenRepository持久性的token的Bean，在数据库的表名一定是persistent_logins,默认的
    @Autowired
    private DataSource dataSource;

  /**
   * 在 WebSecurityConfig 中注入 dataSource ，创建一个 PersistentTokenRepository 的Bean：
   *
   *2.2 数据库存储 使用 Cookie 存储虽然很方便，但是大家都知道 Cookie 毕竟是保存在客户端的，而且 Cookie
   * 的值还与用户名、密码这些敏感数据相关，虽然加密了，但是将敏感信息存在客户端，毕竟不太安全。
   *Spring security 还提供了另一种相对更安全的实现机制：在客户端的 Cookie
   * 中，仅保存一个无意义的加密串（与用户名、密码等敏感数据无关），然后在数据库中保存该加密串-用户信息的对应关系，自动登录时，用 Cookie
   * 中的加密串，到数据库中验证，如果通过，自动登录才算通过。
   *
   * 自动登录基本原理 当浏览器发起表单登录请求时，当通过 UsernamePasswordAuthenticationFilter 认证成功后，会经过
   * RememberMeService，在其中有个 TokenRepository，它会生成一个 token，首先将 token 写入到浏览器的 Cookie 中，然后将
   * token、认证成功的用户名写入到数据库中。 当浏览器下次请求时，会经过 RememberMeAuthenticationFilter，它会读取 Cookie 中的 token，交给
   * RememberMeService 从数据库中查询记录。如果存在记录，会读取用户名并去调用 UserDetailsService，获取用户信息，并将用户信息放入Spring Security
   * 中，实现自动登陆
   * 在数据库的表名一定是persistent_logins,默认的，生成的token会被security机制自动写到该表中
   * @return
   */
  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 如果token表不存在，使用下面语句可以初始化该表；若存在，会报错。
//        tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    /**
     *  配置认证用户信息和权限
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       /**密码是明文，未加密*/
       auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
              @Override
              public String encode(CharSequence charSequence) {
                 System.out.println("jjj");
                 return charSequence.toString();
              }

              @Override
              public boolean matches(CharSequence charSequence, String s) {
                  System.out.println("iii");
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
        .authenticated()
        .and()
        // 设置登陆页，启动程序后，不管访问什么，都会要先来到登录页面来登录
        .formLogin()
        .loginPage("/login")
        // 设置登陆成功页,登录成功可以访问所有的页面
        .defaultSuccessUrl("/home")
        .permitAll()
        // 自定义登陆用户名和密码参数，默认为username和password
        //                .usernameParameter("username")
        //                .passwordParameter("password")
        .and()
        .logout()
        .permitAll()
        // 自动登录，当我们登陆时勾选自动登录时，会自动在 Cookie 中保存一个名为 remember-me 的cookie，
        // 默认有效期为2周，其值是一个加密字符串。在登陆页添加自动登录的选项，注意自动登录字段的 name 必须是 remember-me，然后就会和rememberMe()匹配
        .and()
        .rememberMe()
        // 使用注入的token存储库
        .tokenRepository(persistentTokenRepository())
        // 有效时间：单位s
        .tokenValiditySeconds(60)
        // 使用的用户信息
        .userDetailsService(userDetailsService);

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
