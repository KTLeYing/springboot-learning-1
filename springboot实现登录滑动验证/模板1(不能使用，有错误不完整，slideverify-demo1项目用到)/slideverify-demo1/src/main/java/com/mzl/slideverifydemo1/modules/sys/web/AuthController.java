package com.mzl.slideverifydemo1.modules.sys.web;

import com.google.code.kaptcha.Producer;
import com.mzl.slideverifydemo1.comm.controller.BaseController;
import com.mzl.slideverifydemo1.comm.util.GeetestConfig;
import com.mzl.slideverifydemo1.comm.util.GeetestLib;
import com.mzl.slideverifydemo1.comm.util.IdGen;
import com.mzl.slideverifydemo1.modules.sys.entity.User;
import com.mzl.slideverifydemo1.modules.sys.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class AuthController extends BaseController {
    @Autowired
    private Producer captchaProducer = null;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ModelAttribute
    public User get(@RequestParam(required=false) String id) {
        User entity = null;
        if (StringUtils.isNotBlank(id)){
            entity = userService.get(id);
        }
        if (entity == null){
            entity = new User();
        }
        return entity;
    }

    @RequestMapping(value = {"","home"},method = {RequestMethod.GET,RequestMethod.POST})
    public String home(@ModelAttribute("message") String message,Model model){
        model.addAttribute("message",message);
        return "user/login";
    }

    @RequestMapping(value = "login",method = {RequestMethod.POST})
    public String login(User user,HttpServletRequest request){
        String kaptcha=request.getParameter("kaptcha");
        System.out.println(userService.get("fdsfe32rwerwerwe"));
        return "index";
    }

    @RequestMapping(value = "kaptcha")
    public ModelAndView getKaptchaImage(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        System.out.println("capText: " + capText);

        try {
            String uuid=IdGen.uuid().trim().toString();
            redisTemplate.opsForValue().set(uuid, capText,60*5,TimeUnit.SECONDS);
            Cookie cookie = new Cookie("captchaCode",uuid);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    @RequestMapping(value = "reg",method = {RequestMethod.GET})
    public String path(){
        return "user/reg";
    }
    @RequestMapping(value = "startCaptcha",method = {RequestMethod.GET})
    public void startCaptcha(HttpServletRequest request,
                             HttpServletResponse response) throws IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String resStr = "{}";
        String userid = "test";

        //???????????????,???????????????
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //????????????id
        param.put("client_type", "web"); //web:????????????????????????h5:????????????????????????????????????????????????????????????web_view???native???????????????SDK??????APP???????????????
        param.put("ip_address", "127.0.0.1"); //???????????????????????????????????????IP

        //?????????????????????
        int gtServerStatus = gtSdk.preProcess(param);
        //???????????????????????????session???
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        //???userid?????????session???
        request.getSession().setAttribute("userid", userid);

        resStr = gtSdk.getResponseStr();
        PrintWriter out = response.getWriter();
        out.println(resStr);
    }
    @RequestMapping(value = "verifyLogin",method = {RequestMethod.POST})
    public String verifyLogin(User user,HttpServletRequest request,
                              HttpServletResponse response, RedirectAttributes model) throws IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
        //???session?????????gt-server??????
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        //???session?????????userid
        String userid = (String)request.getSession().getAttribute("userid");
        //???????????????,???????????????
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //????????????id
        param.put("client_type", "web"); //web:????????????????????????h5:????????????????????????????????????????????????????????????web_view???native???????????????SDK??????APP???????????????
        param.put("ip_address", "127.0.0.1"); //???????????????????????????????????????IP
        int gtResult = 0;
        if (gt_server_status_code == 1) {
            //gt-server????????????gt-server??????????????????
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            logger.info("gt-server????????????????????????"+gtResult+"");
        } else {
            // gt-server???????????????????????????failback????????????
            logger.info("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            logger.info("gt-server????????????????????????????????????"+gtResult+"");
        }
        if (gtResult == 1) {
            // ????????????
            List<User> users=userService.getUserByLoginName(user);
            if(users.size()>0){
                //????????????
                if(!user.getPassword().equals(users.get(0).getPassword())){
                    model.addAttribute("message","??????????????????");
                    return "redirect:";
                }
            }else{
                model.addAttribute("message","??????????????????");
                return "redirect:";
            }
           return "index";
        }
        else {
            // ????????????
            model.addAttribute("message","???????????????????????????!");
            return "redirect:";
        }
    }
}