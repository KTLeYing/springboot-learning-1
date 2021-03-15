package com.mzl.websocketdemo1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.server.ServerEndpoint;

/**
 * @ClassName :   WebSocketConfig
 * @Description: websocket配置类
 * @Author: mzl
 * @CreateDate: 2020/11/10 19:15
 * @Version: 1.0
 */
@Configuration
public class WebSocketConfig {

    /**
     * ServerEndpointExporter 作用
     * 这个Bean会自动注册使用@ServerEndpoint注解声明的websocket endpoint,使起作用
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
