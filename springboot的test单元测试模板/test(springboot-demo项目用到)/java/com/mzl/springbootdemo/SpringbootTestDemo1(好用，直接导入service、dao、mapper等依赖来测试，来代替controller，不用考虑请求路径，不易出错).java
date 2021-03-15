package com.mzl.springbootdemo;

import com.mzl.springbootdemo.dao.UserRepository;
import com.mzl.springbootdemo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName :   SpringbootTestDemo1
 * @Description: 直接导入service、dao、mapper等依赖来测试，来代替controller，这样就不需要controller的请求路径了，不会那么容易出错和复杂
 * @Author: mzl
 * @CreateDate: 2020/8/10 22:21
 * @Version: 1.0
 */
//@RunWith: 在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，你要根据需要选择不同的Runner来运行你的测试代码
//@SpringBootTest: classes属性指定加载启动类，SpringBootTest.WebEnvironment.RANDOM_PORT经常和测试类中@LocalServerPort一起在注入属性时使用,会随机生成一个端口号。
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootTestDemo1 {

    //注入service或dao或repository或controller等依赖都可以，像在controller里面一样注入service依赖，来调用业务逻辑服务接口
   //【注意：但是我们一般在这里注入service层的依赖来模拟controller进行单元测试，主要是测试业务逻辑层的逻辑实现过程，而不是注入repository或dao层；一般不在test测试类这边打印(这里只是相当于模拟controller给你进入service层的入口)，直接在service层进行打印输出测试即可，这样可以减少我们的处理花费和方便我们后面直接去对接controller层】

        //注入service依赖###（一般注入service依赖测试）
        @Autowired
        private CommentService commentService;

        @Test
        public void getReplyList() {
	int comId = 1;
            //我们不需要在此处打印，直接调用service的方法(这里只是相当于模拟controller给你进入service层的入口)在                 业务逻辑具体实现过程中打印自己需要打印的数据信息测试即可
	commentService.getReplyList(comId);
       }
       
        @Test
       public void findByName() {
           //我们不需要在此处打印，直接调用service的方法(这里只是相当于模拟controller给你进入service层的入口)在业            务逻辑具体实现过程中打印自己需要打印的数据信息测试即可
            commentService.findAllReply();
      }


   //注入Repository依赖
    @Autowired
    private UserRepository userRepository;

    @Test
    public void findUserById(){
        int uid = 1;
        System.out.println(userRepository.findById(uid).get());
    }


    //注入controller依赖，模拟http接口测试工具
    @Autowired
    private TicketController ticketController;

    @Test
    public void reduceCount() {
           int ticketId = 1;
           int num = 3500;
          for (int i = 1 ; i <= num; i++){
	ticketController.reduceCount(ticketId, i);
           }
    }


}
