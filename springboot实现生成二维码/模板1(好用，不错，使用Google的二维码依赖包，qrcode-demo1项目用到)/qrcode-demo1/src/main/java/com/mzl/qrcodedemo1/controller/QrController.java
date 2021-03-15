package com.mzl.qrcodedemo1.controller;

import com.mzl.qrcodedemo1.utils.QrCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @ClassName :   QrController
 * @Description: 生成二维码控制器
 * @Author: mzl
 * @CreateDate: 2020/12/19 10:51
 * @Version: 1.0
 */
@Controller
public class QrController {

    /**
     * 直接输出二维码图片（输出普通的文字和链接均可，BitMatrix会自动识别链接）
     * @param request
     * @param response
     */
    @RequestMapping("/createQrCode")
    public void createQrCode(HttpServletRequest request, HttpServletResponse response){
        StringBuffer stringBuffer = new StringBuffer();
        try{
            OutputStream outputStream = response.getOutputStream();
            //content:需要生成二维码的连接，logoPath：内嵌图片的路径，outputStream：响应输出流，needCompress:是否压缩内嵌的图片
            QrCodeUtils.encode("http://www.baidu.com", "/static/image/3.jpg", outputStream, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在HTML页面上输出二维码图片（输出普通的文字和链接均可，BitMatrix会自动识别链接）
     * @param
     */
    @RequestMapping("/createQrCode1")
    public String createQrCode1(){
        return "qrcode";
    }

}
