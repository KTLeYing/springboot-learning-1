package com.mzl.ehcachedemo1.controller;

import com.mzl.ehcachedemo1.entity.User;
import com.mzl.ehcachedemo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName :   UserController
 * @Description: 用户控制器
 * @Author: mzl
 * @CreateDate: 2020/11/20 18:22
 * @Version: 1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired(required = true)
    private CacheManager cacheManager;

  /**
   * ####查询所有的用户(ehcache综合测试)#######
   * （1）在配置有上述 application.properties ehcache.xml 等配置文件的 SpringBoot
   * 项目中，且配置有 spring.jpa.show-sql = true 的前提下，再综合 Dao 、Service 和 Controller，通过对控制台打印的信息进行分析
   * （2）在第一次查询时，在执行业务逻辑层findAllUser()方法前，先判断方法( @Cacheable)的缓存策略中的某个缓存对象key是否存在，发现缓存策略中没有该缓存对象的值，于是就执行该方法，
   * Users 的信息是从数据库中查询得到的，因为打印了 Hibernate: select users0_.id as id1_0_, users0_.name as name2_0_
   * from t_user users0_，最后把数据库返回的数据结果值存储到缓存对象中，再把结果返回给上一层。
   * （3）在第二次查询时，再去执行findAllUser()方法，在执行业务逻辑层findAllUser()方法前，先判断方法( @Cacheable)的缓存方法空，
   * 发现缓存策略中存在该缓存对象，即有值(第一次查询存储的)，于是就不执行该方法，我们只能在缓存中获取缓存对象值，所以方法没有打印
   * Hibernate sql 语句，紧接着第一次从缓存中取值是有值的，所以说明第二次查询是从缓存中取的，并且缓存中有所有Users 的值 ；
   * （4）回到 Controller中，在第一次从缓存中取值后面，即在第二次从缓存中取值前，有清除缓存的代码 cache.clear(); ，说明代码 cache.clear();
   * 生效，缓存策略中的所有缓存对象被清除掉了。这个可通过第三次查询得知，因为第三次查询打印了 Hibernate sql 语句，这就更加证实了 cache.clear();
   * 生效了；所以第二次从缓存中取值时是取不到值的，因为缓存对象被清除了，不存在值了；然后接着当第三次查询时，在执行业务逻辑层findAllUser()方法前，
   * 先判断方法( @Cacheable)的缓存策略中的某个缓存对象key是否存在，发现缓存策略中没有该缓存对象的值，于是就执行该方法，
   * Users 的信息是从数据库中查询得到的，打印了 Hibernate: select users0_.id as id1_0_, users0_.name as name2_0_ from
   * t_user users0_，最后把数据库返回的数据结果值存储到缓存对象中，再把结果返回给上一层；
   * （5）第三次从缓存中取值，此时缓存策略中是存在该缓存对象的，即有值(第三次查询存储的)，是不会执行业务逻辑层findAllUser()方法和访数据库的，只能从缓存中获取数据，和第一次从缓存中取的值一致。
   * （6）注意：一般要在对数据看库进行更新(增、删、改)方法上使用，使缓存失效，重新从数据中进行查询并将新的结果缓存到Ehcache缓存对象中，达到缓存同步效果。因此在更新(增、删、改)方法上使用 @CacheEvict
   * 注解也可清空缓存。
   * （7）综上所述，在SpringBoot 中整合Encache实现了数据的缓存。
   * @return
   */
  @RequestMapping("findAllUser")
  public List<User> findAllUser(User user1) {

        //确保第一次查询是从数据库查询的数据，所以将缓存清除
        //使用@CacheEvict 清除缓存（或用cache.clear();来清除）
        userService.addUser(user1);

        //第一次查询
        System.out.println("---------------第一次查询----------------");
        List<User> userList1 = userService.findAllUser();
        for (User user : userList1){
            System.out.println(user);
        }

        //第二次查询
        System.out.println("---------------第二次查询----------------");
        userService.findAllUser();

        //第一次从缓存中获取查询的值
        System.out.println("---------------第一次从缓存中取值----------------");
        Cache cache = cacheManager.getCache("userCache");   //获取自定义的缓存策略（缓存）
        ValueWrapper valueWrapper = cache.get("user.findAllUser");    //获取该该key对应的缓存对象
        List<User> userList = new ArrayList<>();
        if (valueWrapper != null){
            userList = (List<User>) valueWrapper.get();  //获取缓存对象的值
            for (User user : userList) {   //遍历userList列表
                System.out.println(user);
            }
        }else {
            System.out.println("缓存中没有该缓存对象");
        }

        //使用 CacheManager 清空缓存
        cache.clear();

        //第二次从缓存中获取查询的值
        System.out.println("---------------第二次从缓存中取值----------------");
        valueWrapper = cache.get("user.findAllUser");
        if (valueWrapper != null){
            userList = (List<User>) valueWrapper.get();
            for (User user : userList){
                System.out.println(user);
            }
        }else {
            System.out.println("缓存中没有该缓存对象");
        }

        //第三次查询
        System.out.println("---------------第三次查询----------------");
        List<User> userList2 = userService.findAllUser();
        for (User user : userList2){
            System.out.println(user);
        }

        //第三次从缓存中获取查询的值
        System.out.println("---------------第三次从缓存中取值----------------");
        valueWrapper = cache.get("user.findAllUser");
        if (valueWrapper != null){
            userList = (List<User>) valueWrapper.get();
            for (User user : userList) {
                System.out.println(user);
            }
        }else {
            System.out.println("缓存中没有该缓存对象");
        }

        return userList;
    }


    /**
     * 查询所有的用户
     * @return
     */
    @RequestMapping("findAllUser1")
    public List<User> findAllUser1(){
        Cache cache = cacheManager.getCache("userCache");   //获取缓存
        ValueWrapper valueWrapper = cache.get("user.findAllUser");  //获取缓存中的缓存对象
        if (valueWrapper != null){    //缓存对象存在，有数据值，从缓存中获取获取数据
            return (List<User>) valueWrapper.get();
        }else {  //从数据库中获取
            return userService.findAllUser();
        }
    }

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    @RequestMapping("findUserById")
    public User findUserById(Long id){
        Cache cache = cacheManager.getCache("userCache");   //获取缓存
        ValueWrapper valueWrapper = cache.get("user.findAllUser");  //获取缓存中的缓存对象
        if (valueWrapper != null){    //缓存对象存在，有数据值，从缓存中获取获取数据
            List<User> userList = (List<User>) valueWrapper.get();
            for (User user : userList){
                if (user.getId() == id){
                    return user;
                }
            }
            return null;
        }else {  //从数据库中获取
            return userService.findUserById(id);
        }
    }

    /**
     * 添加用户
     * @return
     */
    @RequestMapping("addUser")
    public String addUser(User user){
        return userService.addUser(user);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("updateUser")
    public String updateUser(User user){
        return userService.updateUser(user);
    }

    /**
     * 通过id删除用户
     * @param id
     * @return
     */
    @RequestMapping("deleteUser")
    public String deleteUser(Long id){
        return userService.deleteUser(id);
    }

}
