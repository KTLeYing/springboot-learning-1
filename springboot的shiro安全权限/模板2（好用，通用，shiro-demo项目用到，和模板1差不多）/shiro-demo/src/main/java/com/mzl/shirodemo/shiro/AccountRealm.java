package com.mzl.shirodemo.shiro;

import com.mzl.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName :   AccountRealm
 * @Description:用户授权和认证，可以有1个或多个Realm，主要提供认证和授权的数据；
 * @Author: mzl
 * @CreateDate: 2020/9/16 19:43
 * @Version: 1.0
 */

/**
 * 认证和授权分别调用的realm是AuthenticatingRealm和AuthorizingRealm。说明源码里面已经经过了一些封装，所以我们就不能再直接继承Realm，
 * 那么AuthenticatingRealm和AuthorizingRealm我们继承哪个呢？我们发现AuthorizingRealm是继承AuthenticatingRealm的，所以在重写realm的时候，
 * 我们只需要集成超类AuthorizingRealm即可。
 */
public class AccountRealm extends AuthorizingRealm {

  /**
   * shiro认证工作原理 上面图片中，根据序号，其实我们大概能猜出里shiro的认证流程： <1>Subject进行login操作，参数是封装了用户信息的token <2>Security
   * Manager进行登录操作 <3>Security Manager委托给Authenticator进行认证逻辑处理
   * <4>调用AuthenticationStrategy进行多Realm身份验证 <5>调用对应Realm进行登录校验，认证成功则返回用户属性，失败则抛出对应异常
   */

  @Autowired private UserService userService;

  /**
   * 授权方法
   * shiro授权工作原理
   * 从上图中，我们可以知道授权流程如下： <1>调用Subject.isPermitted/hasRole接口 <2>委托给SecurityManager
   * <3>而SecurityManager接着会委托给Authorizer <4>Authorizer会判断Realm的角色/权限是否和传入的匹配
   * <5>匹配如isPermitted/hasRole会返回true，否则返回false表示授权失败
   *
   * 权限，很多人自然会想起权限系统，涉及到几个关键对象：
   * <1>主体（Subject）
   * <2>资源（Resource）
   * <3>权限（Permission）
   * <4>角色（Role）
   * <5>通过这几个要素，可以设计出比较合理的权限系统
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        AccountProfile principal = (AccountProfile) principalCollection.getPrimaryPrincipal();

        // 硬编码（赋予用户权限或角色）
        if(principal.getUsername().equals("admin")){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            return info;
        }

        return null;
    }

  /**
   * 认证方法
   * 上面图片中，根据序号，其实我们大概能猜出里shiro的认证流程：
   * <1>Subject进行login操作，参数是封装了用户信息的token
   * <2>Security Manager进行登录操作
   * <3>Security Manager委托给Authenticator进行认证逻辑处理
   * <4>调用AuthenticationStrategy进行多Realm身份验证
   * <5>调用对应Realm进行登录校验，认证成功则返回用户属性，失败则抛出对应异常
   *
   * 常见异常
   * <1>DisabledAccountException（禁用的帐号） <2>LockedAccountException（锁定的帐号）
   * <3>UnknownAccountException（错误的帐号） <4>ExcessiveAttemptsException（登录失败次数过多）
   * <5>IncorrectCredentialsException （错误的凭证） <6>ExpiredCredentialsException（过期的凭证）
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
      //调用service业务逻辑进行具体的验证，调用对应Realm进行登录校验，认证成功则返回用户属性，失败则抛出对应异常
      AccountProfile profile = userService.login(token.getUsername(), String.valueOf(token.getPassword()));

      // 把用户信息存到session中，方便前端展示
      SecurityUtils.getSubject().getSession().setAttribute("profile", profile);
      System.out.println(profile);

      SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(profile, token.getCredentials(), getName());
      System.out.println(info);
      return info;
    }

}
