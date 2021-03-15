package com.mzl.shirodemo3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName :   User
 * @Description: 用户实体类
 * @Author: mzl
 * @CreateDate: 2020/9/23 21:39
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_t")
public class User implements Serializable {   //序列化实体类

    private static final long serialVersionUID = 6469007496170509665L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String salt;

    //用户和角色是多对多的关系
    //@JoinTable来关联两个表，以oinColumns是主操作表的中间表列(user_t)，而inverseJoinColumns是副操作表的中间表列(role_t)。
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_role_t", joinColumns = {@JoinColumn(name = "uid")},
//            inverseJoinColumns = {@JoinColumn(name = "rid")})
//    private List<SysRole> roles;

    /**
     * 对盐进行再次加密
     * @return
     */
    public String getCredentialsSalt(){
        return username + salt + salt;
    }



}
