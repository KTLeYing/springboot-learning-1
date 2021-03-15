package com.mzl.weixinlogindemo1.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.weixinlogindemo1.entity.Constants;
import com.mzl.weixinlogindemo1.util.WechatLoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName :   WechatController
 * @Description: 微信登录控制器
 * @Author: mzl
 * @CreateDate: 2021/2/26 11:48
 * @Version: 1.0
 */
@Controller
public class WechatController {

    @Autowired
    private Constants constants;
    @Autowired
    private WechatLoginUtil wechatLoginUtil;

    //用户信息临时保存处
    private static Object quert;

    /**
     *跳转到微信登录的页面
     * @return
     */
    @RequestMapping("/toWxLogin")
    public String toWxLogin(){
        return "wxLogin";
    }

    @RequestMapping("/wxLoginPage")
    @ResponseBody
    public Map<String, String> wxLoginPage(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        // 设置redirect_uri和state=sessionId以及测试号信息，返回授权url
        String uri = wechatLoginUtil.getAuthorizationUrl("pc", sessionId);
        Map<String, String> data = new HashMap<>();
        data.put("sessionId", sessionId);
        //用来前端生成二维码
        data.put("uri", uri);
        // 生成二维码清除上一个用户的数据
        quert = null;
        return data;
    }

    /**
     * 扫描二维码授权成功，取到code，设置的回调方法
     *
     * @author wangsong
     * @date 2019年6月19日 下午5:58:36
     * @param code
     * @param state
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/pcAuth")
    @ResponseBody
    public String pcCallback(String code, String state, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        // 根据code获取access_token和openId，不懂看微信文档
        String result = wechatLoginUtil.getAccessToken(code);
        JSONObject jsonObject = JSONObject.parseObject(result);
        // String refresh_token = jsonObject.getString("refresh_token");
        String access_token = jsonObject.getString("access_token");
        String openId = jsonObject.getString("openId");
        // 授权成功 --> 根据token和openId获取微信用户信息，不懂看我上一篇文章开始分享的链接
        JSONObject infoJson = wechatLoginUtil.getUserInfo(access_token, openId);
        if (infoJson != null) {
            infoJson.put("openId", openId);
        }
        // 登录成功保存用户数据
        quert = infoJson;
        return "登录成功";
    }

    /**
     * 检测登录状态(获取用户信息) 每秒被调用一次，
     * 登录成功，立马得到用户信息返回前台，并取消监听
     * @author wangsong
     * @return
     * @date 2019年6月19日 下午8:18:38
     */
    @RequestMapping(value = "/getInfoJson")
    @ResponseBody
    public String getInfoJson(HttpSession session){
        System.out.println("kkk");
        if (quert == null){
            return "no";
        }
        return quert.toString();
    }

}
