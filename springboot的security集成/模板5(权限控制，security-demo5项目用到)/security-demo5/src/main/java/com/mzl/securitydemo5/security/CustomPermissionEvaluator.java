package com.mzl.securitydemo5.security;

import com.mzl.securitydemo5.entity.SysPermission;
import com.mzl.securitydemo5.service.SysPermissionService;
import com.mzl.securitydemo5.service.SysRolePermissionService;
import com.mzl.securitydemo5.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName :   CustomPermissionEvaluator
 * @Description: 对 hasPermission() 方法的处理，就需要自定义 PermissionEvaluator
 * @Author: mzl
 * @CreateDate: 2020/11/14 14:53
 * @Version: 1.0
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * 我们需要自定义对 hasPermission() 方法的处理，就需要自定义 PermissionEvaluator，创建类 CustomPermissionEvaluator，实现 PermissionEvaluator 接口。
     * 在 hasPermission() 方法中，参数 1 代表用户的权限身份，参数 2 参数 3 分别和 @PreAuthorize("hasPermission('/admin','r')") 中的参数对应，即访问 url 和权限。
     * 思路如下：
     * 通过 Authentication 取出登录用户的所有 Role
     * 遍历每一个 Role，获取到每个Role的所有 Permission
     * 遍历每一个 Permission，只要有一个 Permission 的 url 和传入的url相同，且该 Permission 中包含传入的权限，返回 true
     * 如果遍历都结束，还没有找到，返回false
     */
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRolePermissionService rolePermissionService;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object targetPermission) {
        // 获得loadUserByUsername()方法的结果
        User user = (User) authentication.getPrincipal();  //principal就是用户、主人来的

        //detail是前端页面的id地址、sessionId和非用户名密码的其他的参数等信息，security只默认获取username和password参数
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        System.out.println(authentication.getCredentials());

        // 获得loadUserByUsername()中注入的角色
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        //遍历角色
        for (GrantedAuthority authority : authorities){
            String roleName = authority.getAuthority();
            Integer roleId = roleService.findByName(roleName).getId();

            // 得到角色所有的权限
            List<SysPermission> permissionList =  permissionService.findByRoleId(roleId);

            // 遍历permissionList
            for (SysPermission permission : permissionList){
                // 获取权限集
                List<String> permissions = permission.getPermissions();
                // 如果访问的设置权限Url和权限用户符合的话，返回true
                if (targetUrl.equals(permission.getUrl()) && permissions.contains(targetPermission)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }



}
