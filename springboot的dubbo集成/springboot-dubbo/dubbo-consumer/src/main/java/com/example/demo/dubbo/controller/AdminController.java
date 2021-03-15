package com.example.demo.dubbo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.example.demo.dubbo.entity.Admin;
import com.example.demo.dubbo.service.AdminService;
import com.example.demo.dubbo.utils.Result;
import com.example.demo.dubbo.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路径：com.example.demo.dubbo.controller
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2018/11/16 15:44
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Reference(filter = "traceIdFilter")
    private AdminService adminService;

    @GetMapping("/login")
    public Result login(Integer id) {
        log.info("login start id:{}", id);
        Admin admin = adminService.getById(id);
        if (admin == null) {
            log.info("login end 查询失败");
            return ResultUtil.error(500, "查询失败！");
        }
        log.info("login end admin:{}", JSON.toJSON(admin));
        return ResultUtil.success(admin);
    }
}
