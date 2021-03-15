package com.example.demo.es.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.es.entity.User;
import com.example.demo.es.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 路径：com.example.demo.es.controller
 * 类名：
 * 功能：《用一句描述一下》
 * 备注：
 * 创建人：typ
 * 创建时间：2019/1/8 10:13
 * 修改人：
 * 修改备注：
 * 修改时间：
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * 方法名：
     * 功能：保存
     * 描述：
     * 创建人：typ
     * 创建时间：2019/1/15 11:39
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @PostMapping("/save")
    public String save(@RequestBody User test) {
        log.info("save 入参：{}", JSON.toJSON(test));
        try {
            userRepository.save(test);
            return "保存成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "保存失败！";
    }

    /**
     * 方法名：
     * 功能：根据ID查询
     * 描述：
     * 创建人：typ
     * 创建时间：2019/1/15 11:39
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @GetMapping("/findById")
    public Object findById(Long id) {
        log.info("findById 入参：id:{}", id);
        Optional optional = userRepository.findById(id);
        log.info("findById 出参：{}", JSON.toJSON(optional));
        return optional;
    }

    /**
     * 方法名：
     * 功能：获取所有数据
     * 描述：
     * 创建人：typ
     * 创建时间：2019/1/15 11:39
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @GetMapping("/find")
    public Object find() {
        log.info("find 入参:{}");
        Object object = userRepository.findAll();
        log.info("find 出参:{}",JSON.toJSON(object));
        return object;
    }

    /**
     * 方法名：
     * 功能：分页查询
     * 描述：
     * 创建人：typ
     * 创建时间：2019/1/15 11:39
     * 修改人：
     * 修改描述：
     * 修改时间：
     */
    @GetMapping("/pageFind")
    public Object pageFind(Integer pageNum, Integer pageSize){
        log.info("pageFind 入参：pageNum:{},pageSize:{}",pageNum,pageSize);
        Page<User> pageFind = userRepository.findAll(new PageRequest(pageNum,pageSize));
        log.info("pageFind 出参:{}",JSON.toJSON(pageFind));
        return pageFind;
    }
}
