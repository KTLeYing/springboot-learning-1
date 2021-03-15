package com.mzl.redissiondemo;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class RedissionDemoApplication {

	/**
	 * 添加redissionClient到spring容器作为原料
	 * @return
	 */
	@Bean(destroyMethod="shutdown")
	public RedissonClient redisson() {
		Config config = new Config();
		//集群redis，可以配置多个redis服务器
//		config.useClusterServers()
//				.addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
		//单个redis服务器
		config.useSingleServer()
				.setAddress("redis://localhost:6379");
		return Redisson.create(config);
	}

	/**
	 * 配置缓存管理器
	 * @param redissonClient
	 * @return
	 */
	@Bean
	public CacheManager cacheManager(RedissonClient redissonClient) {
		Map<String, CacheConfig> config = new HashMap<>();

		// create "testMap" cache with ttl = 24 minutes and maxIdleTime = 12 minutes
		config.put("testMap", new CacheConfig(24*60*1000, 12*60*1000));
		return new RedissonSpringCacheManager(redissonClient, config);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedissionDemoApplication.class, args);
	}


}
