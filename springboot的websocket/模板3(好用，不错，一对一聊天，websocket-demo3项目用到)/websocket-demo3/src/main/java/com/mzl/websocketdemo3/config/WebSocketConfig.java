package com.mzl.websocketdemo3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ClassName :   WebSocketConfig
 * @Description: websocket的配置
 * @Author: mzl
 * @CreateDate: 2020/11/11 11:22
 * @Version: 1.0
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
