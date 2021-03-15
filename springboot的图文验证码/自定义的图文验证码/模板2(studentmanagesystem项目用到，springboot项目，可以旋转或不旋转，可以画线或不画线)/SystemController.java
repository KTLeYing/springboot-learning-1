package com.mzl.studentmanagesystem.controller;

import com.mzl.studentmanagesystem.service.StudentService;
import com.mzl.studentmanagesystem.util.Const;
import com.mzl.studentmanagesystem.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @ClassName :   SystemController
 * @Description: 系统控制器
 * @Author: 21989
 * @CreateDate: 2020/7/29 11:02
 * @Version: 1.0
 */
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private StudentService studentService;

    /**
     * 运行后跳转到登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/checkCode")
    public void genterateCpacha(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "vl", defaultValue = "4", required = false) Integer vl, @RequestParam(value = "w", defaultValue = "110", required = false) Integer w, @RequestParam(value = "h", defaultValue = "39", required = false) Integer h){
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        String generatorVcode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute(Const.CODE, generatorVcode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVcode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
