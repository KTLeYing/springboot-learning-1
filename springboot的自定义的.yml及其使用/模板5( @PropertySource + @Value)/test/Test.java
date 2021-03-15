import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestYamlLoader {

    @Autowired
    private MyProperties myProperties;

    @Test
    public void test() {
        System.out.println(myProperties.toString());
    }
}


/**
输出的结果：
MyProperties{name='zjhuang', password='123456', age=25}
**/
 
​
