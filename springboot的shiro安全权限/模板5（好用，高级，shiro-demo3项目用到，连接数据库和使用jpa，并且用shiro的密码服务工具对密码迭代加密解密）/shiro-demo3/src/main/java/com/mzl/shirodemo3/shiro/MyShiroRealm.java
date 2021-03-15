package com.mzl.shirodemo3.shiro;

import com.mzl.shirodemo3.entity.SysPermission;
import com.mzl.shirodemo3.entity.SysRole;
import com.mzl.shirodemo3.entity.User;
import com.mzl.shirodemo3.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.management.relation.Role;
import java.util.List;

/**
 * @ClassName :   MyShiroRealm
 * @Description: shiro中用户自定义登录验证和授权认证的地方（realm）
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:25
 * @Version: 1.0
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //authorizationInfo负责装载role和permission,和配置的role比较，看是否有权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取用户名（在登录验证时，用户名已经加入到principalCollection的PrimaryPrincipal对象中）
        String username = (String) principalCollection.getPrimaryPrincipal();
        //获取用户(使用用户名从数据库中查找)
        User user = userService.findUserByUsername(username);
        System.out.println(user);

        //获取用户的所有角色
        List<SysRole> roles = userService.findSysRoleByUid(user.getId());
        System.out.println("roles: " + roles);
//        user.setRoles(roles);
        //遍历权限和角色，并把名称加入到authorizationInfo中（可能一个用户有多个角色，用for循环来实现添加角色）
        for (SysRole role : roles){
            System.out.println(role.getRole());
            authorizationInfo.addRole(role.getRole());  //给用户添加角色

            //查询每个角色的权限
            List<SysPermission> permissions = userService.findSysPermissionByRid(role.getId());
            System.out.println("permissions: " + permissions);
//            role.setPermissions(permissions);
            //一个角色可能会有多个权限，用for循环来实现
            for (SysPermission permission : permissions){
                System.out.println(permission.getName());
                authorizationInfo.addStringPermission(permission.getName());   //给用户添加权限（包括用户使用角色对应的权限）
            }
        }

        //用户的角色和权限处理完了,返回authorizationInfo授权信息封装对象
        return authorizationInfo;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从token令牌中获取用户名（token里的Principal其实就是装username，credential其实就是装password）
        String username = (String) authenticationToken.getPrincipal();
        //使用用户名和密码查询用户
        User user = userService.findUserByUsername(username);
        if (user == null){
            return null;   //用户为空，则属于用户名错误
        }

        //authenticationInfo包装对象，封装认证的东西（用户名、密码、用户Salt盐值、抽象类CachingRealm的getName()），用于对加盐的密码进行加密，
        // 再和token存储的真正发热password比较是否正确
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),getName());

        System.out.println(authenticationInfo.getPrincipals());
        System.out.println(authenticationInfo.getCredentials());
        System.out.println(authenticationInfo.getCredentialsSalt());
        System.out.println(ByteSource.Util.bytes(user.getCredentialsSalt()));
        System.out.println("getName():" + getName());

        //设置会话跟踪
        SecurityUtils.getSubject().getSession().setAttribute("user", user);

        //返回SimpleAuthenticationInfo认证封装对象
        return authenticationInfo;
    }

}
