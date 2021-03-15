package com.mzl.shirojwtSSO.shiro;

import com.mzl.shirojwtSSO.entity.Permission;
import com.mzl.shirojwtSSO.entity.Role;
import com.mzl.shirojwtSSO.entity.User;
import com.mzl.shirojwtSSO.exception.MyException;
import com.mzl.shirojwtSSO.service.UserService;
import com.mzl.shirojwtSSO.shiro.jwt.JwtToken;
import com.mzl.shirojwtSSO.shiro.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   CustomRealm
 * @Description: 自定义Realm,帮助shiro安全管理器来实现Shiro安全认证
 * @Author: mzl
 * @CreateDate: 2021/1/30 21:43
 * @Version: 1.0
 */
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行用户身份认证——————");
        //获取用户当前请求token
        String token = (String) authenticationToken.getCredentials();
        System.out.println("token:" + token);
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        System.out.println("username:" + username);
        //查询redis中的用户的token是否存在，看是否过期
        String redisToken = stringRedisTemplate.opsForValue().get("token:" + username);
        System.out.println("redisToken:" + redisToken);

        //redis中的token为空说明用户的token已经过期，需重新登录
        if (StringUtils.isEmpty(redisToken)){
            throw new AuthenticationException("认证失败，token已过期，请重新登录！");
        }
        //判断请求的token和redis的token是否一致（因为后面可能使用了第一次登录的token来请求，但是用户名那些都是对的，也会意外放行请求）
        if (!redisToken.equals(token)){
            throw new AuthenticationException("认证失败，token不正确！");
        }
        //验证请求的token是否正确
        if (username == null || !JwtUtil.verify(token, username)){
            throw new AuthenticationException("token认证失败！");
        }

        /* 以下数据库查询可根据实际情况，可以不必再次查询，这里我两次查询会很耗资源
         * 我这里增加两次查询是因为考虑到数据库管理员可能自行更改数据库中的用户信息
         */
        //查询用户是否存在数据库中
        User user = userService.findUser(username);
        if (user == null){
            throw new AuthenticationException("该用户不存在！");
        }
        int ban = user.getBan();
        if (ban == 1){
            throw new AuthenticationException("该用户已被封号！");
        }

        //没抛出异常，则说明用户身份认证成功
        //重新更新用户的redis的有效期(5分钟)，但是用户的key是不变，key的值还有原理的那个token
        stringRedisTemplate.opsForValue().set("token:" + username, token, 300, TimeUnit.SECONDS);
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行权限认证——————");
        //从principal中获取用户的信息
        String username = JwtUtil.getUsername(principalCollection.toString());
        //创建shiro的授权对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取该用户的角色
        List<Role> roleList = userService.getRoles(username);
        System.out.println(roleList);
        //遍历每个角色
        roleList.forEach(e -> {
            //设置添加用户角色
            authorizationInfo.addRole(e.getName());
            //获取每个角色的权限
            List<Permission> permissionList = userService.getPermissions(e.getId());
            System.out.println(permissionList);
            //遍历每个权限
            permissionList.forEach(e1 -> {
                //设置添加用户的权限
                authorizationInfo.addStringPermission(e1.getName());
            });
        });
        //没报错，则说明权限认证成功
        return authorizationInfo;
    }

}
