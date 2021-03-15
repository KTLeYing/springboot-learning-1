package com.mzl.shirodemo1.shiro;

import com.mzl.shirodemo1.entity.Permission;
import com.mzl.shirodemo1.entity.Role;
import com.mzl.shirodemo1.entity.User;
import com.mzl.shirodemo1.service.PermissionService;
import com.mzl.shirodemo1.service.UserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @ClassName :   MyRealm
 * @Description: shiro认证和授权机制
 * @Author: mzl
 * @CreateDate: 2020/9/20 16:58
 * @Version: 1.0
 */
//@Slf4j
public class MyRealm extends AuthorizingRealm {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo：" + principalCollection.toString());
//        log.info("doGetAuthorizationInfo+"+principalCollection.toString());  //与@Slf4j日志注解一起使用

        //使用用户名来查询
        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());

    // 把principals放session中 key=userId value=principals
        System.out.println("kkk");
        System.out.println(String.valueOf(user.getId()) + "===" + SecurityUtils.getSubject().getPrincipals());
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();   //用于授权
        //赋予角色
        for(Role userRole : user.getRoles()){
            logger.info(userRole.getRole());
            info.addRole(userRole.getRole());
        }

        //赋予权限（使用用户id）
        for(Permission permission : permissionService.getByUserId(user.getId())){
      //            if(StringUtils.isNotBlank(permission.getPermCode()))
            logger.info(permission.getName());
            info.addStringPermission(permission.getName());
        }

    // 设置登录次数、时间
    //        userService.updateUserLogin(user);
        logger.info("info:" + info);
        return info;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo："  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username=token.getUsername();
        logger.info(username + " " + token.getPassword());

        User user = userService.getByUserName(token.getUsername());
        if (user != null) {   //用户名和密码正确
//            byte[] salt = Encodes.decodeHex(user.getSalt());
//            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();   //从安全管理获取会话
            session.setAttribute("user", user);    //设置会话
            return new SimpleAuthenticationInfo(username,user.getPassword(),getName());
        } else {
            return null;
        }
//        return null;
    }
}
