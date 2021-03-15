package com.mzl.kaptchademo1.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName :   KaptchaController
 * @Description: 验证码控制器
 * @Author: mzl
 * @CreateDate: 2020/10/25 19:29
 * @Version: 1.0
 */
@Controller
public class KaptchaController {

    /**
     * 导入验证码工具依赖
     */
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/defaultKaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try{
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            //设置验证码session
            request.getSession().setAttribute("rightCode", createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            e.printStackTrace();
            return;
        }
        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        //创建响应输出流对象
        ServletOutputStream responseOutputStream = response.getOutputStream();
        //输出图片
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    /**
     * 校验验证码
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping("/imgvrifyControllerDefaultKaptcha")
    public ModelAndView imgvrifyControllerDefaultKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        //创建一个模型视图对象
        ModelAndView modelAndView = new ModelAndView();
        //获取验证码session
        String rightCode = (String) httpServletRequest.getSession().getAttribute("rightCode");
        String tryCode = httpServletRequest.getParameter("tryCode");
        //rightCode是生成码，tryCode是表单提交码,两个比较
        System.out.println(rightCode);
        System.out.println(tryCode);
        if (!tryCode.equals(rightCode)){
            modelAndView.addObject("info", "验证码错误");  //相当于setParameter（）参数
            modelAndView.setViewName("index");  //返回的视图
        }else {
            modelAndView.addObject("info", "登录成功");
            modelAndView.setViewName("success");
        }
        return modelAndView;
    }

    /**
     * 返回到首页的接口
     * @return
     */
    @RequestMapping("/toIndex")
    public String index(){
        return "index";
    }



}
