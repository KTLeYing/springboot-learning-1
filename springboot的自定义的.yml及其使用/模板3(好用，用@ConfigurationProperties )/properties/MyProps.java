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
* 如果是简单配置用@Value即可,复杂配置用ConfigurationProperties。
 */
@Data
@Component
@ConfigurationProperties(prefix = "config-attributes")
@ToString
public class MyProps {
  private String value;
  private String[] valueArray;
  private List<String> valueList;
  private HashMap<String, String> valueMap;
  private List<Map<String, String>> valueMapList;
}