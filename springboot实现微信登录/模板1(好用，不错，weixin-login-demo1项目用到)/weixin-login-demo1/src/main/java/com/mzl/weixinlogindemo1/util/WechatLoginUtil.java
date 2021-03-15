package com.mzl.weixinlogindemo1.util;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.xml.transform.Source;

import com.mzl.weixinlogindemo1.entity.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName :   WechatLoginUtil
 * @Description: 微信登录工具类
 * @Author: mzl
 * @CreateDate: 2021/2/26 11:41
 * @Version: 1.0
 */
@Component
public class WechatLoginUtil {

    @Autowired
    private Constants constants;

    /**
     * 获取生成的二维码url连接
     *
     * @author wangsong
     * @date 2019年6月19日 下午5:55:36
     * @param appid：公众号的唯一标识
     * @param redirect_uri：授权后重定向的回调链接地址
     * @param response_type：返回类型，填写code
     * @param scope：应用授权作用域，snsapi_base，snsapi_userinfo
     * @param state：非必传，重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @param wechat_redirect：无论直接打开还是做页面302重定向时候，必须带此参数
     * @return
     */
    public String getAuthorizationUrl(String type, String state) {
        // url
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
        String callbackUrl = "";
        Object urlState = "";
        try {
            if ("pc".equals(type)) {
                // pc端回调方法（没处理，我这里回调一致）
                callbackUrl = URLEncoder.encode(constants.getWeCatRedirectUrl() + "/pcAuth", "utf-8");
                urlState = state;
            } else if ("mobile".equals(type)) {
                // pc端回调方法
                callbackUrl = URLEncoder.encode(constants.getWeCatRedirectUrl() + "/pcAuth", "utf-8");
                urlState = System.currentTimeMillis();
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 权限 snsapi_userinfo snsapi_base
        String scope = "snsapi_userinfo";
        url = String.format(url, constants.getWeCatAppId(), callbackUrl, scope, urlState);
        return url;
    }

    /**
     * 获取 token, openId（token有效期2小时）
     *
     * @author wangsong
     * @date 2019年6月19日 下午5:55:11
     * @param code
     * @return
     */
    public String getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
        url = String.format(url, constants.getWeCatAppId(), constants.getWeCatAppSecret(), code);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        if (resp.contains("openid")) {
            JSONObject jsonObject = JSONObject.parseObject(resp);
            String access_token = jsonObject.getString("access_token");
            String openId = jsonObject.getString("openid");
            JSONObject res = new JSONObject();
            res.put("access_token", access_token);
            res.put("openId", openId);
            res.put("refresh_token", jsonObject.getString("refresh_token"));
            return res.toJSONString();
        } else {
            return null;
        }
    }

    /**
     * 微信接口中，token和openId是一起返回，故此方法不需实现
     *
     * @param accessToken
     * @return
     */
    public String getOpenId(String accessToken) {
        return null;
    }

    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
        url = String.format(url, accessToken, openId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        String resp = getRestTemplate().getForObject(uri, String.class);
        if (resp.contains("errcode")) {
            return null;
        } else {
            JSONObject data = JSONObject.parseObject(resp);
            JSONObject result = new JSONObject();
            result.put("id", data.getString("unionid"));
            result.put("sex", data.getString("sex"));
            result.put("nickName", data.getString("nickname"));
            result.put("avatar", data.getString("headimgurl"));
            return result;
        }
    }

    /**
     * 微信的token只有2小时的有效期，过时需要重新获取，所以官方提供了,根据refresh_token
     * 刷新获取token的方法，本项目仅仅是获取用户，信息，并将信息存入库，所以两个小时也已经足够了
     *
     * @author wangsong
     * @date 2019年6月19日 下午5:34:07
     * @param refresh_token
     * @return
     */
    public String refreshToken(String refresh_token) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
        url = String.format(url, constants.getWeCatAppId(), refresh_token);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();
        ResponseEntity<JSONObject> resp = getRestTemplate().getForEntity(uri, JSONObject.class);
        JSONObject jsonObject = resp.getBody();
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    /**
     * 请求的模板
     * @author wangsong
     * @date 2019年6月19日 下午5:35:43
     * @return
     */
    public static RestTemplate getRestTemplate() {// 手动添加
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(120000);
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<>();
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new ResourceHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<Source>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        RestTemplate restTemplate = new RestTemplate(messageConverters);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }

}
