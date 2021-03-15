package com.mzl.shirodemo1.shiro;


import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName :   ShiroConfig
 * @Description: shiro配置类
 * @Author: mzl
 * @CreateDate: 2020/9/20 15:48
 * @Version: 1.0
 */
@Configuration
public class ShiroConfig {

     /**
   * Spring Security权限认证规则 1、当服务器启动时，Spring Security会根据配置将所有的URL和其对应的权限加载到Spring Security中。
   * 2、当发起一个请求时，Spring Security会判断该请求url地址是否需要进行权限和验证，如果不需要，那么直接访问 3、如果这个URL需要进行权限验证，那么Spring
   * Security会检查当前请求来源所属用户是否登录，如果没有登录，则跳转到登录页面，进行登录操作。
   * 4、如果登录，那么判断这个用户所拥有的权限是否包含访问这个URL所需要的权限，如果有则允许访问 如果没有则会报500错误，提示：未在SecurityContext中查找到认证对象
   * 5、如果没有权限，则会提示信息403
   */

    /**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * MyRealm，这是个自定义的认证类，继承自AuthorizingRealm，
     * 负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     */
    @Bean(name = "myRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public MyRealm myRealm() {
        MyRealm realm = new MyRealm();
//        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

//    /**
//     * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
//     * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
//     */
//    @Bean(name = "ehCacheManager")
//    @DependsOn("lifecycleBeanPostProcessor")
//    public EhCacheManager ehCacheManager() {
//        return new EhCacheManager();
//    }

    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类,用realm来帮助securityManager来完成认证和授权工作
     * //
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myRealm());
//        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        Map<String, Filter> filters = new LinkedHashMap<String, Filter>();
        LogoutFilter logoutFilter = new LogoutFilter();
        logoutFilter.setRedirectUrl("/login");  //退出登录，则会跳到login.jsp的页面
//        filters.put("logout",null);
        shiroFilterFactoryBean.setFilters(filters);

    /**
     * 自动地可用的默认的Filter实例是被DefaultFilter枚举类定义的,枚举的名称字段就是可供配置的名称
     *
     * <p>anon---------------org.apache.shiro.web.filter.authc.AnonymousFilter
     * <p>authc--------------org.apache.shiro.web.filter.authc.FormAuthenticationFilter
     * <p>authcBasic---------org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
     * <p>logout-------------org.apache.shiro.web.filter.authc.LogoutFilter
     * <p>noSessionCreation--org.apache.shiro.web.filter.session.NoSessionCreationFilter
     * <p>perms--------------org.apache.shiro.web.filter.authz.PermissionAuthorizationFilter
     * <p>port---------------org.apache.shiro.web.filter.authz.PortFilter
     * <p>rest---------------org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
     * <p>roles--------------org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
     * <p>ssl----------------org.apache.shiro.web.filter.authz.SslFilter
     * <p>user---------------org.apache.shiro.web.filter.authz.UserFilter
     *
     * <p>3)通常可将这些过滤器分为两组
     * <p>anon,authc,authcBasic,user是第一组认证过滤器
     * <p>perms,port,rest,roles,ssl是第二组授权过滤器
     * <p>注意user和authc不同：当应用开启了rememberMe时,用户下次访问时可以是一个user,但绝不会是authc,因为authc是需要重新认证的
     * <p>user表示用户不一定已通过认证,只要曾被Shiro记住过登录状态的用户就可以正常发起请求,比如rememberMe
     * <p>说白了,以前的一个用户登录时开启了rememberMe,然后他关闭浏览器,下次再访问时他就是一个user,而不会authc

     * <p>==========================================================================================================================================
     *
     * <p>4)举几个例子
     * <p>/admin=authc,roles[admin] 表示用户必需已通过认证,并拥有admin角色才可以正常发起'/admin'请求
     * <p>/edit=authc,perms[admin:edit] 表示用户必需已通过认证,并拥有admin:edit权限才可以正常发起'/edit'请求
     * <p>/home=user 表示用户不一定需要已经通过认证,只需要曾经被Shiro记住过登录状态就可以正常发起'/home'请求
     *  // authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
     */
    Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        filterChainDefinitionManager.put("/logout", "logout");   //登出管理
        filterChainDefinitionManager.put("/users/getById/**", "authc,roles[user]");    //请求路径/users下资源全部拦截(需登陆后和有有权限才能查看)
        filterChainDefinitionManager.put("/users/**", "authc,roles[admin]");   //请求路径/users下资源全部拦截(需登陆后和有有权限才能查看)，参数可写多个,多个时必须加上引号,且参数之间用逗号分割
        filterChainDefinitionManager.put("/events/**", "authc,roles[ROLE_ADMIN]");  //请求路径/events下资源全部拦截(需登陆后和有有权限才能查看)
//        filterChainDefinitionManager.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
        filterChainDefinitionManager.put("/**", "anon"); // 静态资源不拦截,anno是用来释放资源
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/");  // 登录成功后要跳转的连接的路径
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");  // 未授权界面，跳转的页面403
        return shiroFilterFactoryBean;
    }

    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**监听拦截器
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }

}
