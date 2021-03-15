package com.mzl.activemqdemo2.utils;

import java.util.Random;

/**
 * @ClassName :   RandomUtils
 * @Description: 随机生成6位验证码
 * @Author: mzl
 * @CreateDate: 2020/10/25 10:03
 * @Version: 1.0
 */
public class RandomUtils {

    public static String randomCode(){
        StringBuilder stringBuilder = new StringBuilder();
        String str = "0123456789";
        for (int i = 0; i < 6; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            stringBuilder.append(ch);
        }

        return stringBuilder.toString();
    }
}
