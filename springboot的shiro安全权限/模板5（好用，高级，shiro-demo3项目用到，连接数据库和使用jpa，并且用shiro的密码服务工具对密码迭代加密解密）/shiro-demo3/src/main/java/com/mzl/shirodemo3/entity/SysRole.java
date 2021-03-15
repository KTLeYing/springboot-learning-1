package com.mzl.shirodemo3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.jsf.FacesContextUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Permission;
import java.util.List;

/**
 * @ClassName :   SysRole
 * @Description: 系统角色实体类
 * @Author: mzl
 * @CreateDate: 2020/9/23 21:46
 * @Version: 1.0
 */
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;

//    //用户与角色之间是多对多的关系
//    @ManyToMany
//    @JoinTable(name = "user_role_t", joinColumns = {@JoinColumn(name = "rid")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
//    List<User> users;
//
//    //角色与权限之间是多对多的关系
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "role_permission_t", joinColumns = {@JoinColumn(name = "rid")}, inverseJoinColumns = {@JoinColumn(name = "pid")})
//    // @JoinTable来关联两个表，以joinColumns是主操作表的中间表列，而inverseJoinColumns是副操作表的中间表列。
//    private List<SysPermission> permissions;


}
