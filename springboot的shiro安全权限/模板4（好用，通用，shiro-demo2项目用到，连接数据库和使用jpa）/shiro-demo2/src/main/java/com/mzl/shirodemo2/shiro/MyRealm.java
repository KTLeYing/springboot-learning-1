package com.mzl.shirodemo2.shiro;

import com.mzl.shirodemo2.entity.User;
import com.mzl.shirodemo2.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName :   MyShiro
 * @Description: 自定义的shiro
 * @Author: mzl
 * @CreateDate: 2020/9/21 20:25
 * @Version: 1.0
 */
public class MyRealm extends AuthorizingRealm {

  /**
   * Spring Security权限认证规则 1、当服务器启动时，Spring Security会根据配置将所有的URL和其对应的权限加载到Spring Security中。
   * 2、当发起一个请求时，Spring Security会判断该请求url地址是否需要进行权限和验证，如果不需要，那么直接访问 3、如果这个URL需要进行权限验证，那么Spring
   * Security会检查当前请求来源所属用户是否登录，如果没有登录，则跳转到登录页面，进行登录操作。
   * 4、如果登录，那么判断这个用户所拥有的权限是否包含访问这个URL所需要的权限，如果有则允许访问 如果没有则会报500错误，提示：未在SecurityContext中查找到认证对象
   * 5、如果没有权限，则会提示信息403
   */

  @Autowired private UserService userservice;

    /**
     * 用户授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        System.out.println("username:" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获得该用户角色
        String role = userservice.findUserByUsername(username).getRole();
        Set<String> set = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        set.add(role);
        //设置该用户拥有的角色
        info.addRole(role);

        System.out.println(info.getRoles());
        return info;
    }

    /**
     * 身份验认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        //从数据库中获取用户对应的密码
        User user = userservice.findUserByUsername(usernamePasswordToken.getUsername());
        if (user != null){
            SecurityUtils.getSubject().getSession().setAttribute("user", user);
            return new SimpleAuthenticationInfo(usernamePasswordToken.getPrincipal(), user.getPassword(), getName());
        }else {
            return null;
        }

    }

}
