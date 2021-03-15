package com.mzl.slideverifydemo1.modules.sys.entity;

import com.mzl.slideverifydemo1.comm.entity.BaseEntity;

public class User extends BaseEntity<User> {
    private String loginName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}