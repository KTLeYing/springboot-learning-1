package com.mzl.zfbpaydemo2.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName :   AlipayConfig
 * @Description: 阿里支付配置类
 * @Author: mzl
 * @CreateDate: 2021/2/24 9:37
 * @Version: 1.0
 */
@Configuration
@Data
public class AlipayConfig {

    @Value("${alipay.gateway_url}")
    private String gatewayUrl;

    @Value("${alipay.app_id}")
    private String appId;

    @Value("${alipay.merchant_private_key}")
    private String merchantPrivateKey;

    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    @Value("${alipay.sign_type}")
    private String signType;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.uid}")
    private String sellerId;

    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    /**
     * 使起作用
     * @return
     */
    @Bean
    AlipayClient alipayClient(){
        return new DefaultAlipayClient(gatewayUrl, merchantPrivateKey, "json", charset, alipayPublicKey, signType);
    }

}
