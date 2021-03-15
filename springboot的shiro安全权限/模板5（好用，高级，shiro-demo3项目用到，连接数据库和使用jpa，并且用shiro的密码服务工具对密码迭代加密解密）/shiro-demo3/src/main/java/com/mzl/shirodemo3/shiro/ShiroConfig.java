package com.mzl.shirodemo3.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   ShiroConfig
 * @Description: shori拦截器配置
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:26
 * @Version: 1.0
 */
@Configuration
public class ShiroConfig {

    /**
     * 注册shiro过滤拦截器工厂，在类加载运行时自动执行使用
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        //创建shiro过程
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //把securityManager添加到过程中去管理各种安全的事情
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置登录页面，用户如果没登录认证，则跳转到此登录页面，如果不设置，默认跳转到login.jsp页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //如果用户没有权限时，将会跳转到该页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthc");
        //如果认证和授权成功
        shiroFilterFactoryBean.setSuccessUrl("/index/home");

        //设置shiro的拦截器，告诉shiro要拦截那些路径，和以前认证权限的东西
        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        //不会被拦截所有的路径
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unanuthc", "anon");
        filterChainDefinitionMap.put("/doLogin", "anon");
        //授权资源，只有登录认证了才能访问，并且还要有对应的权限(autc是指认证；role和perms都是对权限的操作，roles指系统角色权限，perms指具体权限)
        filterChainDefinitionMap.put("/authc/index", "authc");
        filterChainDefinitionMap.put("/authc/admin", "roles[admin]");
        filterChainDefinitionMap.put("/authc/renewable", "perms[Create,Update]");
        filterChainDefinitionMap.put("/authc/removable", "perms[Delete]");
        filterChainDefinitionMap.put("/authc/retrievable", "perms[Retrieve]");
        //把拦截链添加到shiro的过滤工厂
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        System.out.println("shiro工厂配置成功...");
        //返回shiro拦截工厂
        return shiroFilterFactoryBean;
    }

    /**
     * 注册安全管理机制到spring容器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * 注册自定义发的realm授权认证到spring容器
     */
    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //给授权认添加一个密码匹配器， HashedCredentialsMatcher 正是 CredentialsMatcher 的一个实现类
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 注册设置算法和迭代到spring容器
     * 密码匹配，Shiro 提供了用于加密密码和验证密码服务的 CredentialsMatcher 接口，而 HashedCredentialsMatcher 正是 CredentialsMatcher 的一个实现类。
     *  写项目的话，总归会用到用户密码的非对称加密，目前主流的非对称加密方式是 MD5 ，以及在 MD5 上的加盐处理，而 HashedCredentialsMatcher也允许我们指定自己的算法和盐。
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME); //散列算法
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);   //散列次数
        return hashedCredentialsMatcher;
    }

    /**
     * 注册密码迭代加密到spring容器
     * @return
     */
    @Bean
    public PasswordHelper passwordHelper(){
        return new PasswordHelper();
    }

}
