
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 14:47
 * To change this template use File | Settings | File Templates.
 * Description:测试业务代码
 */
public class TestGetToken {

    @Test
    void testToken(){

    }

    @Test
    void jsonHashMap(){
        HashMap<String,Object> map = new HashMap<String, Object>();
        DocumentContext documentContext = JsonPath.parse(this.getClass().getResourceAsStream("/data/create.json"));
        map.entrySet().forEach(entry->{
            documentContext.set(entry.getKey(),entry.getValue());
        });
        String body = documentContext.toString();
    }

    @Test
    void TestFastjson(){

    }
}
