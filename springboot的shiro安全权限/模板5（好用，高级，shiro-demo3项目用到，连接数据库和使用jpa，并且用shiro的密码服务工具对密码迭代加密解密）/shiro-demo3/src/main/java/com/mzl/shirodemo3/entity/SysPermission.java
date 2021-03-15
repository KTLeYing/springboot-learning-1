package com.mzl.shirodemo3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName :   SysPermission
 * @Description: 系统权限实体类
 * @Author: mzl
 * @CreateDate: 2020/9/23 22:08
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission_t")
public class SysPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //权限名
    private String name;
//
//    @ManyToMany
//    @JoinTable(name = "role_permission_t", joinColumns = {@JoinColumn(name = "pid")}, inverseJoinColumns = {@JoinColumn(name = "rid")})
//    // @JoinTable来关联两个表，以joinColumns是主操作表的中间表列，而inverseJoinColumns是副操作表的中间表列。
//    private List<SysRole> roles;

    
}
