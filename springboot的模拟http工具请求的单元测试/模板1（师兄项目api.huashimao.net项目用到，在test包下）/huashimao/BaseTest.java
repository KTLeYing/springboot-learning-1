package net.huashimao;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * 测试基类
 *
 * @author winfans
 * @date 2020/10/15
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class BaseTest {

    @Before
    public void before() {
      log.info("test start");
    }

    @After
    public void after() {
        log.info("test end");
    }
}
