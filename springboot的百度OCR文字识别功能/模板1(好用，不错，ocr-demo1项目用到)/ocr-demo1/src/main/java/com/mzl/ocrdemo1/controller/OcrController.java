package com.mzl.ocrdemo1.controller;

import com.baidu.aip.ocr.AipOcr;
import com.mzl.ocrdemo1.util.JsonUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   OcrController
 * @Description: 文字识别控制器
 * @Author: mzl
 * @CreateDate: 2021/3/9 10:42
 * @Version: 1.0
 */
@Controller
public class OcrController {

    @Value("${ocr.AppId}")
    private String AppId;
    @Value("${ocr.APIKey}")
    private String APIKey;
    @Value("${ocr.SecretKey}")
    private String SecretKey;

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("/ocr")
//    @ResponseBody
    public String ocr(MultipartFile file, Model model) throws Exception {
        //初始化百度接口
        AipOcr client = new AipOcr(AppId, APIKey, SecretKey);
        //传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");  //中英语言
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");

        // 参数为二进制数组
        byte[] buf = file.getBytes();
        JSONObject res = client.basicGeneral(buf, options);
        System.out.println(res.toString());
        Map resultMap = JsonUtil.json2map(res.toString());

        //打印出所有的文字
        List list = (List) resultMap.get("words_result");
        int len = list.size();
        String str = "";
        System.out.println("提取到的文字为:");
        for (int i = 0; i < len;i++) {
            System.out.println(((Map) list.get(i)).get("words"));
            str = str + ((Map) list.get(i)).get("words") + "<br>";
        }

        model.addAttribute("res", str);
        return "index";
    }


}
