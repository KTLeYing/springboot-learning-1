package com.mzl.weixinlogindemo1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * qq 登陆常量配置类
 */
@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Constants {

	@Value("${WechatAppId}")
	private String weCatAppId;

	@Value("${WechatAppSecret}")
	private String weCatAppSecret;

	@Value("${RedirectUrl}")
	private String weCatRedirectUrl;

}
