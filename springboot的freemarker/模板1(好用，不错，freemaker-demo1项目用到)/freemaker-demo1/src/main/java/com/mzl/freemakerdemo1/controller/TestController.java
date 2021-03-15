package com.mzl.freemakerdemo1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName :   TestController
 * @Description: TODO
 * @Author: mzl
 * @CreateDate: 2020/11/21 17:36
 * @Version: 1.0
 */
@Controller
public class TestController {

    /**
     * freemaker测试
     * @return
     */
    @RequestMapping("hello")
    public String hello(ModelMap modelMap){
        modelMap.addAttribute("msg",  "Hello MZL! This is freemarker!");
        return "hello";
    }

    /**
     * freemaker测试表格
     * @param modelMap
     * @return
     */
    @RequestMapping("table")
    public String table(ModelMap modelMap){
        List<Map> mapList = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Map map = new HashMap();
            map.put("name", "MZL_" + i);
            map.put("age", 10 + i);
            map.put("phone", "1365270714" + i);
            mapList.add(map);
        }

        modelMap.put("users", mapList);
        modelMap.put("title", "用户列表");
        return "table";
    }

}
