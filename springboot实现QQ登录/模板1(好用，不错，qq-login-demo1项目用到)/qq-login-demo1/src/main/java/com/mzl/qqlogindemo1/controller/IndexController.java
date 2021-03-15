package com.mzl.qqlogindemo1.controller;

import com.alibaba.fastjson.JSONObject;
import com.mzl.qqlogindemo1.entity.User;
import com.mzl.qqlogindemo1.util.QQConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName :   IndexController
 * @Description: QQ登录实体类
 * @Author: mzl
 * @CreateDate: 2021/2/25 16:58
 * @Version: 1.0
 */
@Controller
public class IndexController {

    @Autowired
    private QQConnectionUtil qqConnectionUtil;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping("/")
    public String toIndex(){
        //        第一步 获取QQ登录按钮url 自己拼接，这是只是演示步骤，无实际意义
        qqConnectionUtil.getUrl();
        return "index";
    }

    /**
     * QQ授权登录
     * @param code
     * @param state
     * @param model
     * @return
     */
    @RequestMapping("/login")
    public Object login(String code, String state, Model model){
        User user = new User();
        //第二步 获取QQ互联返回的code
        System.out.println("第二步：获取QQ互联返回的code=" + code);
        //第三步 获取access token
        String accessToken = qqConnectionUtil.getAccessToken(code);
        System.out.println("accessToken:" + accessToken);
       //第四步 获取登陆后返回的 openid、appid 以JSON对象形式返回
        JSONObject userInfo = qqConnectionUtil.getUserOpenID(accessToken);
        //第五步获取用户有效(昵称、头像等）信息  以JSON对象形式返回
        String oauth_consumer_key = userInfo.getString("client_id");
        System.out.println("oauth_consumer_key:" + oauth_consumer_key);
        String openid = userInfo.getString("openid");
        System.out.println("openid:" + openid);
        JSONObject userRealInfo = qqConnectionUtil.getUserInfo(accessToken, oauth_consumer_key, openid);
        user.setOpenid(openid);
        user.setNickName(userRealInfo.getString("nickname"));
        user.setAvatar(userRealInfo.getString("figureurl_qq"));
        model.addAttribute("user", user);
        return "index";
    }

}
