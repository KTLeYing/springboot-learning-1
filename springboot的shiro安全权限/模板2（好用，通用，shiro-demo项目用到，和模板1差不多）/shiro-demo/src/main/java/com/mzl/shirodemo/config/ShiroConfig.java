package com.mzl.shirodemo.config;

import com.mzl.shirodemo.shiro.AccountRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   ShiroConfig
 * @Description: shiro配置
 * @Author: mzl
 * @CreateDate: 2020/9/16 19:39
 * @Version: 1.0
 */
@Configuration   //这些配置信息注册到spring容易，作为组成成分原料来使用
public class ShiroConfig {

    /**
   * Spring Security权限认证规则 1、当服务器启动时，Spring Security会根据配置将所有的URL和其对应的权限加载到Spring Security中。
   * 2、当发起一个请求时，Spring Security会判断该请求url地址是否需要进行权限和验证，如果不需要，那么直接访问 3、如果这个URL需要进行权限验证，那么Spring
   * Security会检查当前请求来源所属用户是否登录，如果没有登录，则跳转到登录页面，进行登录操作。
   * 4、如果登录，那么判断这个用户所拥有的权限是否包含访问这个URL所需要的权限，如果有则允许访问 如果没有则会报500错误，提示：未在SecurityContext中查找到认证对象
   * 5、如果没有权限，则会提示信息403
   */
   
    /**
     * 自定义的reaml来实现认证和授权，加到spring容器中作为原料来使用
     * @return
     */
    @Bean
    AccountRealm accountRealm() {
        return new AccountRealm();
    }

    /**
     * 默认的安全管理器，会让realm去实现用户的认证和授权，
     * Shiro的心脏；所有具体的交互都通过SecurityManager进行控制；负责所有Subject、且负责进行认证和授权、及会话、缓存的管理。
     * @param accountRealm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        return securityManager;
    }

    /**
     * 相当于过滤器（用shiro过滤链来过滤请求访问路径）
     * @return
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();

        // logged in users with the 'admin' role
        chainDefinition.addPathDefinition("/admin/**", "authc, roles[admin]");

        // logged in users with the 'document:read' permission
        chainDefinition.addPathDefinition("/docs/**", "authc, perms[document:read]");

        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/doLogin", "anon");

        // all other paths require a logged in user
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }



}
