package com.mzl.securitydemo7.security;

import com.mzl.securitydemo7.entity.SysRole;
import com.mzl.securitydemo7.entity.SysUser;
import com.mzl.securitydemo7.entity.SysUserRole;
import com.mzl.securitydemo7.service.SysRoleService;
import com.mzl.securitydemo7.service.SysUserRoleService;
import com.mzl.securitydemo7.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :   CustomUserDetailsService
 * @Description: 自定义 UserDetailsService ，将用户信息和权限注入进来。
 * @Author: mzl
 * @CreateDate: 2020/11/12 21:11
 * @Version: 1.0
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private SysUserRoleService userRoleService;

  /**
   * 我们需要重写 loadUserByUsername 方法，参数是用户输入的用户名。返回值是UserDetails，这是一个接口，
   * 一般使用它的子类org.springframework.security.core.userdetails.User，它有三个参数，分别是用户名、密码和权限集。
   *
   * 端登录页面表单的用户名的name一定是“username”， loadUserByUsername(String username)自动识别username匹配
   *
   * @param username
   * @return
   * @throws UsernameNotFoundException
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //存储用户角色权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        //从数据库中获取用户信息
        SysUser user = userService.findByName(username);

        System.out.println("uuu");

        //判断用户是否存在
        if (user == null){
            throw new UsernameNotFoundException("用户名不存在");   //使用security自带的认证的异常
        }

        //添加角色权限，一个用户可以有多个角色
        List<SysUserRole> userRoleList = userRoleService.findByUserId(user.getId());
        for (SysUserRole userRole : userRoleList) {
            //根据角色id查询角色
            SysRole role = roleService.findById(userRole.getRoleId());
            //添加用户角色权限
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        System.out.println("rrr");

        // 返回UserDetails实现类,这个user是security自带的用户信息存储类,存储用户的用户名、密码和权限
        return new User(user.getName(), user.getPassword(), authorities);
    }

}
