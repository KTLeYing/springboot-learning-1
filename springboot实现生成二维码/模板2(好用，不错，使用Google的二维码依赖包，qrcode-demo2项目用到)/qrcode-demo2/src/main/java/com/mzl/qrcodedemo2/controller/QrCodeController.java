package com.mzl.qrcodedemo2.controller;

import com.mzl.qrcodedemo2.utils.QrCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName :   QrCodeController
 * @Description: 生成二维码控制器
 * @Author: mzl
 * @CreateDate: 2020/12/19 11:50
 * @Version: 1.0
 */
@Controller
public class QrCodeController {

    /**
     * 直接访问输出二维码（输出普通的文字和链接均可，BitMatrix会自动识别链接）
     * @param codeContent
     * @param response
     */
    @RequestMapping("/getQrCode")
    public void getQrCode(String codeContent, HttpServletResponse response){
        System.out.println(codeContent);
        try{
            //调用二维码工具类生成二维码并输出到页面
            QrCodeUtils.createCodeToOutputStream(codeContent, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在HTML页面输出二维码（输出普通的文字和链接均可，BitMatrix会自动识别链接）
     * @return
     */
    @RequestMapping("/getQrCode1")
    public String getQrCode1(){
        return "qrcode";
    }


}
