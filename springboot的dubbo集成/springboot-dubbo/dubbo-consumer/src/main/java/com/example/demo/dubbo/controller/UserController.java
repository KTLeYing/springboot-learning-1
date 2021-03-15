package com.example.demo.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.entity.User;
import com.example.demo.dubbo.service.UserServcie;
import com.example.demo.dubbo.utils.Result;
import com.example.demo.dubbo.utils.ResultUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 路径：com.example.demo.dubbo.controller
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/13 17:49
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(filter = "traceIdFilter")
    private UserServcie userServcie;

    @GetMapping("/getById")
    public Result getById(Integer id) {
        log.info("getById  start id:{}", id);
        User user = userServcie.getById(id);
        log.info("getById  end id:{}", JSON.toJSON(user));
        return ResultUtil.success(user);
    }

    @GetMapping("/find")
    public Result findUser(@RequestParam(required = false, defaultValue = "1") int pageNum, @RequestParam(required = false, defaultValue = "10") int pageSize) {
        log.info("findUser  start pageNum:{},pageSize:{}", pageNum, pageSize);
        PageInfo<User> users = userServcie.findUser(pageNum, pageSize);
        log.info("findUser  end users:{}", JSON.toJSON(users));
        return ResultUtil.success(users);
    }

    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        log.info("save  start user:{}", JSON.toJSON(user));
        int row = userServcie.save(user);
        if (row > 0) {
            log.info("save  end 保存成功");
            return ResultUtil.success(row);
        }
        log.info("save  end 保存失败");
        return ResultUtil.error(500,"保存失败!");
    }

    @GetMapping("/del")
    public Result delete(Integer id) {
        log.info("delete  start id:{}", id);
        userServcie.delete(id);
        log.info("delete  end 删除成功");
        return ResultUtil.success(null);
    }

}
