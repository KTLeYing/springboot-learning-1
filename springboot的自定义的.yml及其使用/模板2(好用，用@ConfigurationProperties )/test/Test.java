package com.jobgo.gateway.interceptor;

/**
 * @ClassName :   CsrfInterceptor
 * @Description: 自定义csrf拦截器（实现HandlerInterceptor接口）
 * @Author: mzl
 * @CreateDate: 2020/9/22 10:45
 * @Version: 1.0
 */
@Component
@Slf4j
@EnableConfigurationProperties   //可以注入属于配置属性的依赖
public class Test {
      
@Autowired  //注入依赖来使用
private MyProps myProps; 
	
@Test
public void propsTest() throws JsonProcessingException {
	System.out.println("simpleProp: " + myProps.getSimpleProp());
	System.out.println("arrayProps: " + objectMapper.writeValueAsString(myProps.getArrayProps()));
	System.out.println("listProp1: " + objectMapper.writeValueAsString(myProps.getListProp1()));
	System.out.println("listProp2: " + objectMapper.writeValueAsString(myProps.getListProp2()));
	System.out.println("mapProps: " + objectMapper.writeValueAsString(myProps.getMapProps()));
	}

/**
输出的结果：
simpleProp: simplePropValue
arrayProps: ["1","2","3","4","5"]
listProp1: [{"name":"abc","value":"abcValue"},{"name":"efg","value":"efgValue"}]
listProp2: ["config2Value1","config2Vavlue2"]
mapProps: {"key1":"value1","key2":"value2"}
**/
 
​
}
