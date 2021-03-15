package com.mzl.springbootdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @ClassName :   SpringbootTestDemo1
 * @Description: Springboot Test测试，利用MockMvc进行测试。
 * @Author: mzl
 * @CreateDate: 2020/8/10 22:21
 * @Version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SpringbootTestDemo2 {

    @Autowired
    private MockMvc mvc;

    @Test
    public void test() throws Exception{
        this.mvc.perform(MockMvcRequestBuilders.get("/health")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("OK"));
    }


}
