package com.mzl.logincontrol.pojo;

public class CurrentUser {

    private static final ThreadLocal<UserBO> currentUser = new ThreadLocal<>();

    //设置线程的当前登录的user用户
    public static void put(UserBO userBO) {
        currentUser.set(userBO);
    }
    //获取线程的当前登录的用户user
    public static UserBO get() {
        return currentUser.get();
    }

}
