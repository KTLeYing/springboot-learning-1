package com.mzl.logincontrol.service;

import com.mzl.logincontrol.pojo.UserBO;

public interface UserService {

    String buildUserInfo(UserBO userBO);

    void logout(String jwt);

}
