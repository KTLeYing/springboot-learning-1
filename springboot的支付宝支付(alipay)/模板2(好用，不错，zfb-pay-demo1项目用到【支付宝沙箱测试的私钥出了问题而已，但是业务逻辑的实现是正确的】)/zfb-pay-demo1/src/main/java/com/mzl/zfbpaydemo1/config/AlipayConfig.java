package com.mzl.zfbpaydemo1.config;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileWriter;

/**
 * @ClassName :   AlipayConfig
 * @Description: 阿里支付宝支付基础配置类
 * @Author: mzl
 * @CreateDate: 2021/2/20 20:53
 * @Version: 1.0
 */
public class AlipayConfig {


    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    //  商户应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号,开发时使用沙箱提供的APPID，生产环境改成自己的APPID
    public static String APP_ID = "2021000116675892";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCL2dZ3adfl6wQPvJ5Ujh2yyG4/WpRSADalrbCz+xvaBIFUQWRLD5jbkrGLh/ei3fMJnvv4gQeOSy7fnDlZyHZF1UT8vgwA+nQYH2vH9CVs+ZOO0N/Ve9rZ1KKyuk2pk+YZrHQKA6/qH4VZ8zYJjFJLX8PQu/LjcnmGNkx1jh0mNwB5L2USDRbDiKWBK+Gtt07Rd4yqep4JRC0hD1fw9Yb4frFjxyvLUmZFDQqBKboeB+COY9KDDAyCyLyXFn7V/GQZqQTU50Vtzv6CyAsAgsTG9uB36SZIMTuwX9mhDd//MHsFI6aVhBojzAyy+mxFEdaqfOQBxWANVo+xz+Z89G8dAgMBAAECggEAKMKT1Hf/2d4Wb/O8fc5x73KlV0eyJPg09TLbZomQg4l+nCz26z2fgpeNsaouTcS8mhhBPht1NU7KWZfxyy428oGHOta1Lu/QOFuoET4iTlGrZRuRsWoIziZASTEtg3g8v0zZATYz5lvLhPzyyfOg5KEDtGmjEBGEQ869mCGUc1dxZ3Lc3csFBjw2He4RrhJdvjgABlhcuB349f67IawmHxnIzrDHgmRIT1ewCDsO6nU61vHFGubBlwvH7DHDLIw2KhYsu/9B3yM9RAqPhr5zD+JfIAbaMlizOBe08YCICFhYv0C/QmEbcr0lNZuqRdUScpO0oRTQQ9Jp5z9clx+prQKBgQDtLWZHX61+ak/+FJAo/DuuPNVWWaJhVgU65Ap29X+h0HqRz3EQfv6xVRPJJi83JXzzsdlh4A7sqxJIz2/uTmBr8wY8o0zkVo+GJ7sseeYFCycM8DfVv97DqbqZpCv/eXwGbKluABoAR5Oium8+/WOgjzYxDogte3UHgz6VmcwSiwKBgQCW8xyKvl7A0gqID4aeKwCRjHK8yo95cm+RulKVoNs0+leDusDXSriyuyQqNeEgG4jUmcnYpN3xl+zvAJM03cS0BbcFp7EqSG7zF2kVdVY9x3PmGbnV5cPtI6Y9wp2VniHpdPRPdb1VHU7dHVJWtrOithKB1UPoU0F096QRcnQB9wKBgQDFXldTo8oTfdLNZD1G7geYCiWaMwMOru+xPx16OveYJ44oEpFLIfM+jMbnR/BYmsitXt3ANh0VU5LmpkDF9dEFY/pXsakGBwZupFd9Pn9syQgZGznLlVyCRJb0zgqTEXxlOD2SPWFlxCqWa0CyEVi7vxFgp3ApMSiFNPF6ur542wKBgB6Qf1kAjwMQBJ3kNufT4TuXaqwbf82hhEHHrm0psvkZL5s2t1FgieF2zy3U1w6/RId31E0PpPkEQC9suGHd/bRN6+8zM0ZSgFSsitSTe7Dqr+cJkn1jFrbvnvumam4fJNK2obltJ7cSRiOzijaQq/LoVvr/Cc8C/KAIfKojuKWlAoGAYFOOj5JJ77eJZcGalscYTyYPTrqQvUK6jrfDfu4BkEj9yeyZQdd8sQC6EwyI52caql5BUKhzPu5kkEoyA8cVvdKSPAJBXLa/E5wPiSLA4eO1BumMmxWEbyrpQR6N1MndXMSMuvZ/y5unvxVks/H9Q4GkpTJZwhsncLaYgy4nps4=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwlfOyKRLI9JzPwqb+r4oHtcJXmfZcvyPJMZh4GkYlHyEJa5KYIK9SxakB0mNneYM7Xq3lBlvxySXKv+dEPIs+KqlyQ7IfuIwN7isPT7yW0ITKGoT+V5EcP2fg7i0elLlv7mDp/UJVWDKq+4mIC/jQ+4t39iW+E1NvqF8kc96HQNZu2K8MzmUHP7G/zCUwdnBHsRDWqClmD10+/rowZBO7EpJiCo+3pvgNyC6ZXaGt+4JKvRYUvyolYfWc4cRXlDdRl1yo1R4bzWXJ7ZKHD8Lt1Dl7lEpuAi5XhGJEZENS3Vh7ctopmi56SP85NHkzzCn0LzViGNEYVi1Lw+kmmIEeQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/synNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问(其实就是支付成功后返回的页面)
    public static String return_url = "http://localhost:8080/returnNotify";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 返回格式
    public static String FORMAT = "json";

    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 日志记录目录
    public static String log_path = "D:\\Program Files\\MyFilesPractice(own)\\alipay\\";

    //↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord){
        FileWriter writer = null;
        try{
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if (writer != null){
                try{
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
