package com.mzl.slideverifydemo1.comm.util;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@Lazy(false)
public class IdGen {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }



    /**
     * Activiti ID 生成
     */

    public String getNextId() {
        return IdGen.uuid();
    }


    /*public static void main(String[] args) {
        System.out.println(IdGen.uuid());
        System.out.println(IdGen.uuid().length());
        System.out.println(new IdGen().getNextId());
        for (int i=0; i<1000; i++){
            System.out.println(IdGen.uuid() );
        }
    }*/

}
