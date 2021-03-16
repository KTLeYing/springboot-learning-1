# springboot-learning-1
springboot框架相关技术的案例项目及使用springboot框架时的需注意点

### 项目介绍
SpringBoot整合框架各种实用的组件技术点以及一些框架重要技术点的项目案例的实现，纯属个人技术积累和框架学习，有缺漏之处请指出。  

### 主体版本号
- java v1.8
- springboot v2.0.5.RELEASE

### 各技术点预览目录
| 组件名称 | 版本号 | 描述 |
| ------- | ----- | ---- |
| SpringBoot-ActiveMQ | ----- | SpringBoot整合ActiveQM消息队列 |
| SpringBoot-RabbitMQ | ----- | SpringBoot整合RabbitMQ消息队列| 
| SpringBoot-AOP | ----- | SpringBoot整合AOP面向切面编程的过程实现 |
| SpringBoot-Ehcache | ----- | SpringBoot整合Ehcache高速缓存 |
| SpringBoot-Chart | ----- | Spring Boot整合JFreeChart各种图表绘制类库 |
| SpringBoot-Data-Jpa | ----- | SpringBoot整合Jpa，实现增、删、改、查以及复杂的查询操作 |
| SpringBoot-Docker | ----- | 在Docker中部署SpringBoot项目 |
| SpringBoot-Dubbo | ----- | SpringBoot整合 Dubbo，Apache Dubbo是一个由阿里巴巴开源的基于Java的高性能RPC框架 |
| SpringBoot-Elasticsearch | ----- | SpringBoot集成Elasticsearch的简单实例 |
| SpringBoot-Excel | ----- | SpringBoot整合excel导入导出，包括用POI、EasyPOI和EasyExcel来实现 |
| SpringBoot-Mail | ----- | SpringBoot实现发送邮件，包括springboot自带的邮件发送和自定义的邮件发送的实现 |
| SpringBoot-MongoDB | ----- | SpringBoot集成mongodb数据库 |
| SpringBoot-MyBatis | ----- | SpringBoot整合mybatis的，实现增、删、改、查以及复杂的查询操作 |
| SpringBoot-MyBatis-Plus | ----- | SpringBoot整合MyBatis-Plus，含代码生成器、多数据源配置、CRUD操作等 |
| SpringBoot-Quartz | ----- |  SpringBoot集成Quartz，实现动态配置定时任务|
| SpringBoot-Schedule | ----- |  SpringBoot集成自带的Schedule，实现定时任务 |
| SpringBoot-Redis | ----- | SpringBoot整合Redis和Jedis，包括使用自定义配置的RedisTemplate和默认的RedisTemplate |
| SpringBoot-Redisson | ----- | SpringBoot集成redisson分布式锁的简单实例 |
| SpringBoot-Shiro | ----- | SpringBoot整合Shiro安全框架，实现权限管理实例 |
| SpringBoot-SSO | ----- | SpringBoot实现SSO单点登录操作，包括使用redis+cookies和redis+JWT的方式来实现 |
| SpringBoot-Csrf | ----- | 使用SpringBoot的Security实现防御CSRF跨站请求伪造攻击 |
| SpringBoot-Thymeleaf | ----- | SpringBoot集成Thymeleaf模板引擎 |
| SpringBoot-Freemarker | ----- | SpringBoot集成Freemarkerf模板引擎 |
| SpringBoot-https | ----- | SpringBoot中实现Https安全性请求|
| SpringBoot-Kafka | ----- | SpringBoot整合Kafka，Kafka是基于Zookeeper协调的分布式日志系统，也可以做MQ系统(在这里做消息队列) |
| SpringBoot-Security | ----- | SpringBoot整合Security安全框架，实现身份认证和授权管理 |  
| SpringBoot-JWT | ----- | SpringBoot整合Jwt实现请求身份认证 | 
| SpringBoot-test | ----- | SpringBoot整合Test的单元测试模板 | 
| SpringBoot-WebSocket | ----- | SpringBoot整合WebSocket，实现一对一聊天和群聊 | 
| SpringBoot-Shiro+Jwt+Redis | ----- | SpringBoot整合Shiro+Jwt+Redis，实现请求身份认证和权限管理 |
| SpringBoot-API接口的安全设计 | ----- | SpringBoot实现API接口的安全设计，用于与前端对接的API接口（包括token、timestamp、sign参数) | 
| SpringBoot-Redis+Mybatis整合 | ----- | SpringBoot整合Redis+Mybatis的实例，实现Redis对数据的缓存，减少对数据库的访问 | 
| SpringBoot-百度OCR文字识别 | ----- | SpringBoot实现百度OCR文字识别的功能| 
| SpringBoot-并发登录人数控制 | ----- | SpringBoot实现并发登录人数控制(踢出用户)的实例 | 
| SpringBoot-限流 | ----- | SpringBoot实现接口限流，包括使用阿里的Sentinel、Guava的工具类RateLimiter令牌桶限流和Redis的令牌桶限流三种方式来实现 |
| SpringBoot-@ResponseBody注解返回时间戳 | ----- | SpringBoot的处理@ResponseBody返回时间戳(long)而不是时间类型的问题 | 
| SpringBoot-单机抢票 | ----- | SpringBoot实现单机抢票的实例，用RabbitMQ来实现 | 
| SpringBoot-定时任务 | ----- | SpringBoot的定时任务，包括使用SpringBoot自带的Scheduled和Quartz来实现 | 
| SpringBoot-短信验证码 | ----- | springBoot实现短信验证码发送，包括使用springboot自带的短信发送和自定义的短信发送来实现 | 
| SpringBoot-分页、排序和模糊查询 | ----- | SpringBoot在整合Jpa、Mybatis和Mybatis-Plus等ORM框架的分页、排序和模糊查询等各种复杂的操作的实例 | 
| SpringBoot-高并发秒杀系统 | ----- | Springboot实现高并发秒杀系统，主要使用RabbitMQ+Redis来实现，最后使用JMeter来测压(并发量、QPS等信息) | 
| SpringBoot-过滤器 | ----- | SpringBoot整合WebFilter来实现过滤器过滤的功能 | 
| SpringBoot-跨域处理 | ----- | SpringBoot的跨域处理的实例,包括使用5种方式来实现 | 
| SpringBoot-拦截器 | ----- | SpringBoot的拦截器Interceptor的实现 | 
| SpringBoot-幂等性接口 | ----- | springBoot的幂等性接口的实现，包括使用Redis+Token和Session+Token两种方式来实现 | 
| SpringBoot-模拟http工具请求单元测试 | ----- | SpringBoot的模拟http工具请求的单元测试 | 
| SpringBoot-七牛云存储 | ----- | SpringBoot整合七牛云实现文件存储的实例 |
| SpringBoot-时间戳 | ----- | SpringBoot的时间戳的使用 |
| SpringBoot-统一异常处理 | ----- | SpringBoot的统一异常处理的实现实例  |
| SpringBoot-图文验证码 | ----- | SpringBoot实现图文验证码，包括使用Kaptcha的图文验证码和自定义的图文验证码两种方式来实现 |
| SpringBoot-邮件验证码发送 | ----- | SpringBoot整合邮件验证码发送，使用了springboot自带的邮件发送和自定义的邮件发送来实现 |
| SpringBoot-上传预览多个图片 | ----- | SpringBoot+Vue前后端分离来实现上传预览多个图案例 |
| SpringBoot-MD5加盐加密 | ----- | SpringBoot的MD5加盐加密和解密的实现案例 |
| SpringBoot-mybatis的逆向工程| ----- | SpringBoot整合Mybatis的逆向工程，自动生成代码|
| SpringBoot-自定义.yml文件配置 | ----- | SpringBoot整合自定义的.yml文件配置和使用的实例|
| SpringBoot-QQ授权登录 | ----- | SpringBoot实现QQ授权登录案例 |
| SpringBoot-微信授权登录 | ----- | SpringBoot实现微信授权登录案例 |
| SpringBoot-滑动验证 | ----- | SpringBoot实现滑动验证码案例 |
| SpringBoot-UEditor | ----- | SpringBoot整合百度的UEditor实现富文本编辑案例，Springboot+Vue前后端分离来实现 |
| SpringBoot-生成二维码 | ----- | SpringBoot实现二维码的生成，包括使用Google的二维码依赖和Hutoold的二维码依赖来实现 |
| SpringBoot-通过邮箱激活用户 | ----- | SpringBoot实现通过邮箱激活用户的案例 |
| SpringBoot-上传和下载 | ----- | SpringBoot实现文件(图片)的上传和下载的实例 |
| SpringBoot-修改用户头像 | ----- |  SpringBoot实现修改用户头像的实例 |
| SpringBoot-Swagger | ----- | SpringBoot整合Swagger，实现前后端分离开发时的接口测试以及文档编写的实例  |
| SpringBoot-文件上传并显示进度条 | ----- | SpringBoot实现文件上传并显示进度条的案例 |
| SpringBoot-宝塔面板部署Springboot步骤图片教程 | ----- | 本人使用宝塔面板部署SpringBoot项目步骤图片教程(直接使用Xshell工具的命令行来部署的原理及步骤也是一样的) |
| SpringBoot-创建springboot项目过程截图 | ----- | 新手入门创建SpringBoot项目过程截图教程 |
| SpringBoot-springboot的.yml或.properties文件的各种配置 | ----- | SpringBoot的.yml或.properties文件的各种常用配置的汇集 |
| SpringBoot-springboot各种依赖的汇集 | ----- | SpringBoot各种依赖的汇集 |
| Springboot相关知识点 | ----- | 使用SpringBoot框架时的各种相关知识点和易错点的积累 |
| Springboot项目汇集 | ----- | 本人的Springboot项目的汇集记录（方便自己以后查找） |


### 欢迎关注个人公众号  
CSDN博客：https://blog.csdn.net/weixin_43548310/article/details/114862253
