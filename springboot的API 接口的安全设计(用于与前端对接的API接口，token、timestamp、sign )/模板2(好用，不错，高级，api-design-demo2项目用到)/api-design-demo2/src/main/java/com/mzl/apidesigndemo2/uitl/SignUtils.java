package com.mzl.apidesigndemo2.uitl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;

/**
 * 用户签名工具类
 */
public class SignUtils {

    //加密的密匙
    public static final String secretKeyOfWxh = "e10adc3949ba59abbe56e057f20f883f";

    public static void main(String[] args) {
        //参数签名算法测试
        HashMap<String, String> signMap = new HashMap<String, String>();
        signMap.put("token","a2144337fdcd47daab3ed0623deb1817");
        signMap.put("personId","1");
        signMap.put("timestamp","1609827917");
        System.out.println("得到签名sign1:"+getSign(signMap,secretKeyOfWxh));
    }


    /**
     * 检查校验签名
     * @param request
     * @return
     */
    public static Boolean checkSign(HttpServletRequest request,String sign){
        Boolean flag= false;
        //检查sigin是否过期（是否正确）
//        Enumeration<?> pNames =  request.getParameterNames();   //获取当前请求的所有参数(请求参数方式)
        Enumeration<?> pNames =  request.getHeaderNames();   //获取当前请求的所有参数（请求头的方式）
        Map<String, String> params = new HashMap<String, String>();   //存储进行sign加密所需要的参数（token和timestamp）
        while (pNames.hasMoreElements()) {
            String pName = (String) pNames.nextElement();
            //不需要添加sign，因为这里也是用当前请求的token和时间戳去进行生成正确的sign，再看当前请求sign和生成的真正的sign来比较实现验证是
            if("sign".equals(pName)) {
                continue;
            }
            //只需要token和timestamp的请求头参数
            if (pName.equals("token") || pName.equals("timestamp")){
//                String pValue = (String)request.getParameter(pName);  //获取请求参数的值（请求参数方式）
                String pValue = (String)request.getHeader(pName);   //获取请求头的参数的值（请求头方式）
                params.put(pName, pValue);
            }
        }
        System.out.println("现在的sign-->>" + sign);
        //获取真正的sign
        String realSign = getSign(params,secretKeyOfWxh);
        System.out.println("验证的sign-->>" + realSign);
        //判断用户当前请求的sign和真正的sign（刚刚加密得到的）是否相等
        if(sign.equals(realSign)){
            flag = true;
        }
        return flag;
    }


    public static String utf8Encoding(String value, String sourceCharsetName) {
        try {
            return new String(value.getBytes(sourceCharsetName), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }



    private static byte[] getMD5Digest(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse);
        }
        return bytes;
    }


    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }


    /**
     * 得到签名(签名=时间戳+用户的唯一token+密匙 的三者进行MD5加密生成的)
     * @param params 参数集合不含secretkey
     * @param secret 验证接口的secretkey
     * @return
     */
    public static String getSign(Map<String, String> params,String secret)
    {
        String sign="";
        StringBuilder sb = new StringBuilder();
        //step1：先对请求参数排序（树set，有序的）
        Set<String> keyset = params.keySet();  //获取Map中的key的集合
        TreeSet<String> sortSet = new TreeSet<String>();
        sortSet.addAll(keyset);
        Iterator<String> it = sortSet.iterator();
        //step2：把参数的key value链接起来 secretkey放在最后面，得到要加密的字符串
        while(it.hasNext())
        {
            String key = it.next();
            //获取key对应的值
            String value = params.get(key);
            sb.append(key).append(value);
        }
        sb.append(secret);   //最后添加加密密匙
        System.out.println("加密前的字符串:" + sb.toString());
        byte[] md5Digest;
        try {
            //得到Md5加密得到sign
            md5Digest = getMD5Digest(sb.toString());
            sign = byte2hex(md5Digest);
        } catch (IOException e) {
            System.out.println("生成签名错误");
        }
        return sign;
    }

    public static long getTimestamp(){
        long timestampLong = System.currentTimeMillis();

        long timestampsStr = timestampLong / 1000;   //化为秒

        return timestampsStr;
    }
}
