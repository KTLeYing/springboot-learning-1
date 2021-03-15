package com.mzl.springbootdemo;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

//@RunWith: 在JUnit中有很多个Runner，他们负责调用你的测试代码，每一个Runner都有各自的特殊功能，你要根据需要选择不同的Runner来运行你的测试代码
//@SpringBootTest: classes属性指定加载启动类，SpringBootTest.WebEnvironment.RANDOM_PORT经常和测试类中@LocalServerPort一起在注入属性时使用,会随机生成一个端口号。
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootDemoApplicationTests {

    /**
     * 配置测试类基本配置
     */
    //@LocalServerPort 提供了 @Value("${local.server.port}") 的代替，配置端口号
    @LocalServerPort
    private int port;

    //请求的基本路径
    private URL base;

    //注入请求模板依赖
    @Autowired
    private TestRestTemplate restTemplate;

    //在执行每个测试方法前执行，一般用来初始化方法（比如我们在测试别的方法时，类中与其他测试方法共享的值已经被改变，为了保证测试结果的有效性，我们会在@Before注解的方法中重置数据）
    @Before
    public void setUp() throws Exception{
        String url = String.format("http://localhost:%d", port);
        System.out.println(String.format("port is: [%d]", port));
        this.base = new URL(url);   //给基本路径赋值，初始化
    }

    /**
     * SpringBoot Test的简单测试方法
     * 请求路径为 http://localhost:8080/user/test 【base + controller的具体请求的路径】 地址发送请求
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        //创建请求响应实体类，相当于@ResponsBody，处理http响应，ResponseEntity标识整个http相应：状态码、头部信息以及相应体内容。因此我们可以使用其对http响应实现完整配置。
        ResponseEntity<String> response = this.restTemplate.getForEntity(this.base.toString() + "/user/test", String.class, "");
        System.out.println(String.format("测试结果为： %s", response.getBody()));
    }



}
