package com.mzl.securitydemo7.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.mzl.securitydemo7.exception.MyException;
import com.mzl.securitydemo7.pojo.SmsBean;
import com.mzl.securitydemo7.result.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   SmsUtils
 * @Description: 短信发送工具类（并使用redis来设置短信ttl生存时间）
 * @Author: mzl
 * @CreateDate: 2020/10/25 9:46
 * @Version: 1.0
 */
@Component
public class SmsUtils {

    @Autowired
    private SmsBean smsBean;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送方法1（使用 CommonRequest组装请求和CommonResponse 发送短信响应 --> 使用aliyun-java-sdk-core核心依赖）
     * @param phone
     * @throws ClientException
     */
    public String  sendSms1(String phone) throws Exception {
        System.out.println(smsBean);
        //获取某个手机号短信redis
        String code = (String) redisTemplate.opsForValue().get(phone);

        if (!StringUtils.isEmpty(code)){  //短信还有效，暂时还不能发送
            throw new MyException(CodeEnum.NOT_ALLOW_SEND.getMsg(), CodeEnum.NOT_ALLOW_SEND.getCode()); //不自己处理，抛给上一级（调用者）处理
        }

        //初始化acsClient，支持region区域化（common）
        code = RandomUtils.randomCode();
        //DefaultProfile实现了IClientProfile接口
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsBean.getAccessKeyId(), smsBean.getAccessKeySecret());
        IAcsClient acsClient = new DefaultAcsClient(profile);   //DefaultAcsClient实现了IAcsClient接口

        //组装请求对象
        CommonRequest request = new CommonRequest();
        //请求方法
        request.setMethod(MethodType.POST);
        //产品域
        request.setDomain(smsBean.getDomain());
        //版本
        request.setVersion("2017-05-25");  //固定规定版本
        //实现方法
        request.setAction("SendSms");   //固定规定action
        //接收短信的手机号码，支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
        request.putQueryParameter("PhoneNumbers", phone);
        //短信签名名称，请在控制台签名管理页面签名名称一列查看。
        request.putQueryParameter("SignName", smsBean.getSignName());
        //发送模板码，请在控制台模板管理页面模板CODE一列查看。
        request.putQueryParameter("TemplateCode", smsBean.getTemplateCode());
        //如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为$[code]"时,此处的值为
//        request.putQueryParameter("TemplateParam", "{'code':"+code+"}");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = acsClient.getCommonResponse(request);
            //给此个发送短信的号码设置短信的ttl有效时间,（key,value,long,TimeType）
            redisTemplate.opsForValue().set(phone, code, 300, TimeUnit.SECONDS);  //设置5分钟(300s)过期时间,即用户300s后才能再次发送
            System.out.println(response.getData());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return  code;
    }

    /**
     * 发送方法2（使用 SendSmsRequest组装请求和SendSmsResponse发送短信响应 --> 使用aliyun-java-sdk-dysmsapi依赖）
     * @param phone
     * @throws ClientException
     */
    public void sendSms2(String phone) throws Exception {
        System.out.println(smsBean);
        //获取某个手机号短信redis是否存在
        String code = (String) redisTemplate.opsForValue().get(phone);

        if (!StringUtils.isEmpty(code)){  //短信还有效，暂时还不能发送
            throw new MyException(CodeEnum.NOT_ALLOW_SEND.getMsg(), CodeEnum.NOT_ALLOW_SEND.getCode()); //不自己处理，抛给上一级（调用者）处理
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region区域化
        code = RandomUtils.randomCode();
        //DefaultProfile实现了IClientProfile接口
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsBean.getAccessKeyId(), smsBean.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", smsBean.getProduct(), smsBean.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);  //DefaultAcsClient实现了IAcsClient接口

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //请求方法（可选）
        request.setMethod(MethodType.POST);
        //版本（可选）
        request.setVersion("2017-05-25");  //固定版本
        //(可选)-上行短信扩展码(无特殊需求用户请忽略此字段)，code自己定义
        request.setSmsUpExtendCode("90997");
        //(可选)outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者,id自己定义
        request.setOutId("yourOutId");
        //接收短信的手机号码，支持对多个手机号码发送短信，手机号码之间以英文逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
        request.setPhoneNumbers(phone);
        //短信签名名称，请在控制台签名管理页面签名名称一列查看。
        request.setSignName(smsBean.getSignName());
        //发送模板码，请在控制台模板管理页面模板CODE一列查看。
        request.setTemplateCode(smsBean.getTemplateCode());
        //如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为$[code]"时,此处的值为
//        request.setTemplateParam("{'code':"+code+"}");
        request.setTemplateParam("{\"code\":\""+code+"\"}");
        try {
            SendSmsResponse response = acsClient.getAcsResponse(request);
            //给此个发送短信的号码设置短信的ttl有效时间,（key,value,long,TimeType）
            redisTemplate.opsForValue().set(phone, code, 300, TimeUnit.SECONDS);  //设置5分钟(300s)过期时间,即用户300s后才能再次发送
            System.out.println(response.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


}
