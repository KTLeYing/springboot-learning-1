package com.mzl.csrfdemo2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName :   IndexController
 * @Description: 测试控制器（模拟危险网站）
 * @Author: mzl
 * @CreateDate: 2021/3/11 19:14
 * @Version: 1.0
 */
@Controller
public class IndexController {

    /**
     * 跳转到首页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return "index";
    }

}
