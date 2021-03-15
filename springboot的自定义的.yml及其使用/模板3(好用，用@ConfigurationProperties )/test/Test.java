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
      
 @Autowired
  private MyProps myProps;

  @Test
  public void readConfigNode() {
    System.out.println(myProps.toString());
  }

/**
输出的结果：
Config(value=345, valueArray=[1, 2, 3, 4, 5, 6, 7, 8, 9], valueList=[-13579 -246810], valueMap={name=lili, age=20, sex=female}, valueMapList=[{name=bob, age=21}, {name=caven, age=31}])
**/
 
​
}
