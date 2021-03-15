package net.huashimao;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.huashimao.user.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付测试
 *
 * @author winfans
 * @date 2020/10/15
 */
@Slf4j
public class UserControllerTest extends BaseTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

     /**
   * post请求（使用json字符串参数）模板
   */
    @Test
    public void testSendVerifyCode() throws Exception {
        // 构建请求对象，这里可以是pojo或者dto。
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13025261795");

        /**
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.post("XXX")构造一个请求。
         * 3、ResultActions.accept(MediaType.APPLICATION_JSON))设置返回类型为JSON
         * 4、ResultActions.contentType(MediaType.APPLICATION_JSON) 设置contentType
         * 5、ResultActions.content(JSONObject.toJSONString(map)) 设置请求body
         * 6、ResultActions.andExpect添加执行完成后的断言。
         * 7、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 8、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/code")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(map)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

 /**
   * get请求（普通的get，参数前有 ？号的）模板
   */
    @Test
    public void testDemo() throws Exception {

        MultiValueMap<String, String> stringStringMultiValueMap = new LinkedMultiValueMap<>();
        stringStringMultiValueMap.add("key1", "value1");
        stringStringMultiValueMap.add("key2", "value2");

        /**
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.params添加请求传值
         * 4、ResultActions.accept(MediaType.APPLICATION_JSON))设置返回类型为JSON
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/test")
                .params(stringStringMultiValueMap)
                .header("Authorization", "HuaShiMao-eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyMzAzOTEyNTIzNjE0NzIiLCJzdWIiOiIxMzAyNTI2MTc5NSIsImlhdCI6MTYwMjc3NjU0NSwiaXNzIjoiam9iZ28iLCJhdXRob3JpdGllcyI6IltdIiwiZXhwIjoxNjA0MzY2NDkzfQ.vVGHaqeyqS11TMBP_r40WkWArQ0U59_nDSgfpYexaEwEdX5dRzgZ-3nGBkyg03qT8ME5QZY6_7lIwFHWskv_rg")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }


   /**
   * get请求（@PathVariable路径参数）模板
   */
    @Test
    public void testShowPainter() throws Exception {
//        MultiValueMap<String, String> stringStringMultiValueMap = new LinkedMultiValueMap<>();
//        stringStringMultiValueMap.add("sortFlag", "1");
//        stringStringMultiValueMap.add("curPage", "1");

        /**
         * 1、mockMvc.perform执行一个请求。
         * 2、MockMvcRequestBuilders.get("XXX")构造一个请求。
         * 3、ResultActions.params添加请求传值
         * 4、ResultActions.accept(MediaType.APPLICATION_JSON))设置返回类型为JSON
         * 5、ResultActions.andExpect添加执行完成后的断言。
         * 6、ResultActions.andDo添加一个结果处理器，表示要对结果做点什么事情
         *   比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
         * 7、ResultActions.andReturn表示执行完成后返回相应的结果。
         */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/showPainter/1/1")
//                .params(stringStringMultiValueMap)
                .header("Authorization", "HuaShiMao-eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIyMzAzOTEyNTIzNjE0NzIiLCJzdWIiOiIxMzAyNTI2MTc5NSIsImlhdCI6MTYwMjc3NjU0NSwiaXNzIjoiam9iZ28iLCJhdXRob3JpdGllcyI6IltdIiwiZXhwIjoxNjA0MzY2NDkzfQ.vVGHaqeyqS11TMBP_r40WkWArQ0U59_nDSgfpYexaEwEdX5dRzgZ-3nGBkyg03qT8ME5QZY6_7lIwFHWskv_rg")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }


}
