package com.jobgo.gateway.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName :   CsrfReleaseProperties
 * @Description: CsrfReleaseProperties属性
 * @Author: mzl
 * @CreateDate: 2020/9/22 19:48
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "csrf")
public class CsrfReleaseProperties {

    /**
     * 白名单域名列表
     */
    private List<String> releaseDomainList;


}
