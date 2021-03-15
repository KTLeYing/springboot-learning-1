package com.mzl.springbootdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName :   SpringbootTestDemo1
 * @Description: Springboot Test测试，TestRestTemplate进行测试。
 * @Author: mzl
 * @CreateDate: 2020/8/10 22:21
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootTestDemo3 {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test() throws Exception{
        String result = template.getForObject("/user/test", String.class);
        Assert.assertEquals("0", result);
        String result1 = template.getForObject("/user/test", String.class);
        Assert.assertEquals("1", result1);
    }


}
