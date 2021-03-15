package com.mzl.shirodemo2.shiro;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @ClassName : ShiroConfig @Description: shiro配置类 @Author: mzl @CreateDate: 2020/9/21
 * 20:25 @Version: 1.0
 * Spring Security权限认证规则 1、当服务器启动时，Spring Security会根据配置将所有的URL和其对应的权限加载到Spring
 * Security中。 2、当发起一个请求时，Spring Security会判断该请求url地址是否需要进行权限和验证，如果不需要，那么直接访问
 * 3、如果这个URL需要进行权限验证，那么Spring Security会检查当前请求来源所属用户是否登录，如果没有登录，则跳转到登录页面，进行登录操作。
 * 4、如果登录，那么判断这个用户所拥有的权限是否包含访问这个URL所需要的权限，如果有则允许访问 如果没有则会报500错误，提示：未在SecurityContext中查找到认证对象
 * 5、如果没有权限，则会提示信息403
 *
 * 【综上总结：不管什么情况（除了没有设置认证和权限判断机制，这个可以直接访问），系统首先会进行登录认证，
 * 认证后再进行权限的判断。如果认证失败，则跳转到登录页面认证；认证成功了，如果有对应的权限设置，则进行权限的验证】
 */
@Configuration
public class ShiroConfig {

  /**
   * Shiro拦截器工厂类
   * loginUrl：没有登录的用户请求需要登录的页面时自动跳转到登录页面。
   *
   * <p>unauthorizedUrl：没有权限默认跳转的页面，登录的用户访问了没有被授权的资源自动跳转到的页面。
   *
   * <p>其他的一些配置，如下：
   *
   * <p>successUrl：登录成功默认跳转页面，不配置则跳转至”/”，可以不配置，直接通过代码进行处理。
   *
   * <p>securityManager：这个属性是必须的，配置为securityManager就好了。
   *
   * <p>filterChainDefinitions：配置过滤规则，从上到下的顺序匹配。
   *
   * @param securityManager
   * @return
   */
  @Bean
  public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射，登录界面
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        // 设置无权限时跳转的 url，未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 设置拦截器,把要拦截的请求路径添加到list列表中，配置过滤规则，从上到下的顺序匹配。
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最下面,不然会过拦截掉anon的 :这是一个坑呢，一不小心代码就不好使了;
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //开放登陆接口,anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/notLogin", "anon");
        filterChainDefinitionMap.put("/notRole", "anon");

        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "roles[user]");
//        filterChainDefinitionMap.put("/user/**", "anon");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
         //authc:所有url都必须认证通过才可以访问;其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截,认证拦截，所有的路径都要登录认证，除了/login
        filterChainDefinitionMap.put("/**", "authc");

        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myRealm());
        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public MyRealm myRealm(){
        return new MyRealm();
    }

}
