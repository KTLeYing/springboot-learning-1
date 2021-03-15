package com.mzl.seckilldemo1.controller;

import com.mzl.seckilldemo1.dto.GoodsDTO;
import com.mzl.seckilldemo1.entity.User;
import com.mzl.seckilldemo1.redis.redis.GoodsKey;
import com.mzl.seckilldemo1.redis.redis.RedisService;
import com.mzl.seckilldemo1.redis.redis.UserKey;
import com.mzl.seckilldemo1.service.GoodsService;
import com.mzl.seckilldemo1.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName :   GoodsController
 * @Description: 商品控制器
 * @Author: mzl
 * @CreateDate: 2021/3/5 16:01
 * @Version: 1.0
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;


    /**
     * 跳转到商品列表页面
     * @return
     */
    @RequestMapping(value = "/toList", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, User user){
        System.out.println("ddd");
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        List<GoodsDTO> goodsDTOList = goodsService.listGoods();
        System.out.println(goodsDTOList);
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsDTOList);

        //手动页面缓存渲染
        IWebContext ctx =new WebContext(request,response, request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);

        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        //结果输出
        return html;
    }

    /**
     * 商品的详情页面
     * @return
     */
    @RequestMapping("/detail")
    public String detail(Integer goodsId, Model model, HttpServletResponse response, HttpServletRequest request){
        GoodsDTO goodsDTO = goodsService.detail(goodsId);
        //获取cookies
        Cookie[] cookie = request.getCookies();
        String token = "";
        for(int i = 0; i < cookie.length; i++) {
            if (cookie[i].getName().equals("token")){
               token = cookie[i].getValue();
               break;
            }
        }
        System.out.println(token);
        User user = userService.getByToken(response, token);
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsDTO);
//        model.addAttribute("seckillStatus", 0);
//        model.addAttribute("remainSeconds", 60);
        return "goods_detail";
    }



}
