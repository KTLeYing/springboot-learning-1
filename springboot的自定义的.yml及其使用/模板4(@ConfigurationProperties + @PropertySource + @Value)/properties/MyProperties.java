import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
@PropertySource: 为了告知springboot加载自定义的yml配置文件，springboot默认会自动加载application.yml文件，如果参数信息直接写在这个文件里，则不需要显式加载。
@Value: 指定目标属性在yml’文件中的全限定名。
@Component: 作用是将当前类实例化到spring容器中，相当于xml配置文件中的<bean id="" class=""/>
添加了@ConfigurationProperties注解，并且设置了prefix前缀属性的值，这样@Value只需要指定参数名称，省略了前缀路径。
**/

@Component
@PropertySource(value = "classpath:test.yml")
@ConfigurationProperties(prefix = "system.user")
public class MyProperties {

    @Value("${name}")
    private String name;    //如果这里的name和.yml文件相同，也是name，则@Value("${name}")可以不写，自动同名配置
    @Value("${password}")
    private String password;
    @Value("${age}")
    private int age;

    // Setter...
    // Getter...
    // toString...
}
