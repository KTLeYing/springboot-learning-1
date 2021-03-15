package com.mzl.logincontrol;

import com.mzl.logincontrol.filter.CompareKickOutFilter;
import com.mzl.logincontrol.filter.KickOutFilter;
import com.mzl.logincontrol.filter.QueueKickOutFilter;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class LoginControlApplication {

	/**
	 * 注入redission到spring容器里面作为原料（程序启动时加载配置）
	 * @return
	 */
	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		config.setCodec(new JsonJacksonCodec())
				.useSingleServer()
				.setAddress("redis://localhost:6379");   //设置自己redis的IP地址
		return Redisson.create(config);
	}

	/**
	 * 配置队列踢出方式（只有在application.properties配置文件中开启队列踢出的配置才会能使用）
	 * @return
	 */
//	@ConditionalOnProperty(value = {"queue-filter.enabled"})
//	@Bean
//	public KickOutFilter queueKickOutFilter() {
//		return new QueueKickOutFilter();
//	}

	/**
	 * 配置时间戳比较踢出方式（默认使用时间戳方式）
	 * @return
	 */
	@ConditionalOnMissingBean(KickOutFilter.class)
	@Bean
	public KickOutFilter compareKickOutFilter() {
		return new CompareKickOutFilter();
	}

	/**
	 * 注册过滤器到sping容器里，并配置踢出过滤器，去买个请求之前执行过滤器
	 * @param kickOutFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean testFilterRegistration(KickOutFilter kickOutFilter) {
		System.out.println(kickOutFilter);
		FilterRegistrationBean registration = new FilterRegistrationBean(kickOutFilter);
		registration.addUrlPatterns("/user/*");   //设置拦截的请求路径
		registration.setName("kickOutFilter");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(LoginControlApplication.class, args);
	}

}
