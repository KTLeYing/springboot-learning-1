package com.mzl.shirojwt.shiro;

import com.mzl.shirojwt.constant.CommonConstant;
import com.mzl.shirojwt.dao.PermissionDao;
import com.mzl.shirojwt.dao.RoleDao;
import com.mzl.shirojwt.dao.UserDao;
import com.mzl.shirojwt.dto.UserDTO;
import com.mzl.shirojwt.entity.Permission;
import com.mzl.shirojwt.entity.Role;
import com.mzl.shirojwt.entity.User;
import com.mzl.shirojwt.shiro.jwt.JwtToken;
import com.mzl.shirojwt.shiro.jwt.JwtUtil;
import com.mzl.shirojwt.util.RedisUtil;
import com.mzl.shirojwt.util.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName :   UserRealm
 * @Description: 用户自定义认证类，代替shiro管理器去验证
 * @Author: mzl
 * @CreateDate: 2021/1/27 10:30
 * @Version: 1.0
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 权限的处理
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行了用户的授权操作，看是否为该用户...");
        //创建授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 从PrincipalCollection中获取token进行验证
        String account = JwtUtil.getClaim(principalCollection.toString(), CommonConstant.ACCOUNT);
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(account);
        // 通过账号获取到该用户的角色信息
        List<Role> roleList = roleDao.findRoleListByAccount(userDTO);
        System.out.println(roleList);
        //遍历用户的角色
        for (Role role : roleList){
            if (role != null){
                // 添加该用户的角色信息（这里添加的角色会与shiro配置文件或在接口的角色注解配置的权限去比较，看是否匹配）
                simpleAuthorizationInfo.addRole(role.getName());
                // 根据用户角色查询权限
                List<Permission> permissionList = permissionDao.findPermissionByRole(role);
                System.out.println(permissionList);
                //遍历角色的权限
                for (Permission permission : permissionList){
                    if (permission != null){
                        //添加该用户对应角色的权限（这里添加的权限会与shiro配置文件或在接口的权限注解配置的权限去比较，看是否匹配）
                        simpleAuthorizationInfo.addStringPermission(permission.getAction());
                    }
                }
            }
        }

        return simpleAuthorizationInfo;
    }

    /**
     * 用户认证处理
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行了用户的认证，看是否为该用户...");
        // 获取到token
        String token = (String) authenticationToken.getCredentials();
        System.out.println("lll");
        System.out.println(token);
        // 获取到token信息用于和数据库对比
        String account = JwtUtil.getClaim(token, CommonConstant.ACCOUNT);
        if (StringUtil.isBlank(account)){
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 去数据库查询用户是否存在
        User user = new User();
        user.setAccount(account);
        user =  userDao.selectOne(user);
        if (user == null){   //数据库中不存在该用户
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }

        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && RedisUtil.hasKey(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)){
            // 获取RefreshToken的时间戳
            // Redis中RefreshToken还存在，获取RefreshToken的时间戳
            String currentTimeMillisRedis = RedisUtil.get(CommonConstant.PREFIX_SHIRO_REFRESH_TOKEN + account);
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, CommonConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)){
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }

        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }
}
