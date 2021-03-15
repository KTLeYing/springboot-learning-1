package com.mzl.avoidpostdemo2.service;

import com.mzl.avoidpostdemo2.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @ClassName :   TokenService
 * @Description: token服务
 * @Author: mzl
 * @CreateDate: 2020/11/22 20:54
 * @Version: 1.0
 */
@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    /**
     * 创建一个token
     * @return
     */
    public String createToken(){
        //随机产生(uuid将数字转化为数字字符串)
        String token = UUID.randomUUID().toString();
        //将token存储到redis里面
        Boolean flag = redisService.setEx(token, token, 1000L);
        return token;
    }

    /**
     * 检查请求头和参数是否有token，并验证token
     * @param request
     * @return
     */
    public boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)){
            //如果请求的token为空则从参数中获取
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)){  //参数的token也是空的
                throw new BaseException(2001, "缺少token参数");
            }
        }

        //如果从header中拿到的token不正确
        if (!redisService.exists(token)){
            throw new BaseException(2002, "不能重复提交....token不正确、空");
        }

        //提交订单后，token正确 移除token
        if (!redisService.remove(token)){
            throw new BaseException(2003, "token移除失败");
        }

        return true;
    }

}
