package com.mzl.shirodemo3.shiro;

import com.mzl.shirodemo3.entity.User;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.Random;

/**
 * @ClassName :   PasswordHelper
 * @Description: 代码迭代加密,保证用户密码的安全
 * @Author: mzl
 * @CreateDate: 2020/9/24 1:31
 * @Version: 1.0
 */
public class PasswordHelper {

    //安全的随机字符(利用shiro安全框架的随机数字生成器)
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    //算法名称
    public static final String ALGORITHM_NAME = "md5";
    //迭代次数
    public static final int HASH_ITERATIONS = 2;

    //进行密码加密
    public void encryptPassword(User user){
        //随机字符串作为用户的salt
        String saltHex = randomNumberGenerator.nextBytes().toHex();
        System.out.println("salt:" + saltHex);
        user.setSalt(saltHex);
        //算法、用户密码、用户salt、迭代次数（使用SimpleHash的加密构造器，四个参数分别algorithmName代表进行加密的算法名称；
        // source代表需要加密的元数据，如密码、salt代表盐；需要加进一起加密的数据；hashIterations代表hash迭代次数。），转为hex十六进制
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
        //对用户设置新密码(设置加密后的密码)
        System.out.println("加密后的密码：" + newPassword);
        user.setPassword(newPassword);
    }



}
