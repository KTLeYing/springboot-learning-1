package com.mzl.websocketdemo2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName :   WebsocketConfig
 * @Description: websocket配置类
 * @Author: mzl
 * @CreateDate: 2020/11/10 21:30
 * @Version: 1.0
 */
@Configuration
public class WebsocketConfig {

    /**
     * 注册使用原料，与注解@Endpoint连用起作用，使用了该注解的server，两者一起才能起作用
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
