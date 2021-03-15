package com.mzl.qrcodedemo3.controller;

import com.mzl.qrcodedemo3.utils.QrCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   QrCodeController
 * @Description: 二维码控制器
 * @Author: mzl
 * @CreateDate: 2020/12/19 13:22
 * @Version: 1.0
 */
@Controller
public class QrCodeController {

    @Autowired
    private QrCodeUtils qrCodeUtils;

    /**
     * 直接显示生成二维码图片(显示普通文字或链接均可，BitMatrix类对象自动识别链接)
     * @param codeContent
     * @param response
     */
    @RequestMapping("/getQrCode")
    public void getQrCode(String codeContent, HttpServletResponse response){
        try {
            qrCodeUtils.createQRCode2Stream(codeContent, response);   //生成二维码
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在html页面上显示二维码(显示普通文字或链接均可，BitMatrix类对象自动识别链接)
     * @return
     */
    @RequestMapping("/getQrCode1")
    public String getQrCode1(){
        return "qrcode";
    }


}
