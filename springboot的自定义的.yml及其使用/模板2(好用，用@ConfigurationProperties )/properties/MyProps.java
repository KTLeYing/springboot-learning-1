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
@ConfigurationProperties(prefix="myProps") //接收application.yml中的myProps下面的属性
public class MyProps {
	private String simpleProp;
	private String[] arrayProps;
	private List<Map<String, String>> listProp1 = new ArrayList<>(); //接收prop1里面的属性值
	private List<String> listProp2 = new ArrayList<>(); //接收prop2里面的属性值
	private Map<String, String> mapProps = new HashMap<>(); //接收prop1里面的属性值
	
	public String getSimpleProp() {
		return simpleProp;
	}
	
	//String类型的一定需要setter来接收属性值；maps, collections, 和 arrays 不需要
	public void setSimpleProp(String simpleProp) {
		this.simpleProp = simpleProp;
	}
	
	public List<Map<String, String>> getListProp1() {
		return listProp1;
	}
	public List<String> getListProp2() {
		return listProp2;
	}
 
	public String[] getArrayProps() {
		return arrayProps;
	}
 
	public void setArrayProps(String[] arrayProps) {
		this.arrayProps = arrayProps;
	}
 
	public Map<String, String> getMapProps() {
		return mapProps;
	}
 
	public void setMapProps(Map<String, String> mapProps) {
		this.mapProps = mapProps;
	}
}