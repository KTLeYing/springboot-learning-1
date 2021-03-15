package com.mzl.redisdemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mzl.redisdemo.entity.NBAPlayer;
import com.mzl.redisdemo.service.NBAPlayerService;
import com.mzl.redisdemo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ClassName :   RedisController
 * @Description: redis控制器
 * @Author: mzl
 * @CreateDate: 2020/9/13 17:14
 * @Version: 1.0
 */
@Controller
@RequestMapping("redisController")
public class RedisController {

    //注入redis工具类来使用
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NBAPlayerService nbaPlayerService;

    /**
     *redis测试(不设过期时间的)
     * @param k
     * @param v
     * @return
     */
    @RequestMapping("/redisTest")
    @ResponseBody
    public String redisTest(String k, String v){
        redisUtils.set(k, v);
        return (String) redisUtils.get(k);
    }

    /**
     * redis测试（设置了过期时间的,时间单位为秒）
     * @param k
     * @param v
     * @param t
     * @return
     */
    @RequestMapping("/redisTest1")
    public ResponseEntity<Object> redisTest1(String k, String v, long t){
        redisUtils.set(k, v, t);
        //ResponseEntity类似@ResponseEntity的作用
        return ResponseEntity.ok(redisUtils.get(k)) ;
    }

    /**
     * 获取redis的值
     * @param k
     * @return
     */
    @RequestMapping("/redisTest2")
    @ResponseBody
    public String redisTest2(String k){
        return (String)redisUtils.get(k);
    }

    /**
     * 查询所有的NBA球员
     * @return
     */
    @RequestMapping("/selectAllPlayers")
    @ResponseBody
    public List<NBAPlayer> selectAllPlayers(){
        return nbaPlayerService.selectAllPlayers();
    }

    /**
     * redis作为mybatis缓存
     * 用户第一次访问的时候获取数据库的值，再次访问时直接从缓存中获取数据
     *
     * 设置缓存过期时间
     * @return
     */
    @RequestMapping("/testRedisAndMybatis")
    @ResponseBody
    public Object testRedisAndMybatis(){
        //第一步 先从redis中获取
        String strJson = (String) redisUtils.get("nbaPlayerCache");
        System.out.println(strJson);
        if (strJson == null){   //redis不存在，可能是过期了
            System.out.println("从db中取");
            //第二步 如果在redis中拿不到，则到数据库中获取
            List<NBAPlayer> nbaPlayerList = nbaPlayerService.selectAllPlayers();
            //第三步 db非空的情况刷新redis的值
            if (nbaPlayerList != null){
                //设置NBA球员缓存
                redisUtils.set("nbaPlayerCache", JSON.toJSONString(nbaPlayerList), (long)60); //让实体类转换为json字符串，key-value的形式，方便处理
                return nbaPlayerList;
            }
            return null;
        }else {
            System.out.println("从redis中获取值");
            return JSON.parseArray(strJson, NBAPlayer.class);   //redis中是json字符串，需要转换为实体类的数组对象才能返回（有多个球员，用数组来存储）
        }

    }


}
