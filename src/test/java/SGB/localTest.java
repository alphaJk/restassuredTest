package SGB;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-11-10
 * Time: 10:34
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class localTest extends BaseTest {
    @Test
    @DisplayName("")
    void getVersion(){
        api.getVersion().then().statusCode(200).body("msg",equalTo("OK"));
    }

    @Test
    void getDeviceList(){
        api.getDeviceList().then().statusCode(200).body("msg",equalTo("OK"));
    }

    @Test
    void catalogTest(){
        api.catalog("34020000001110000001",null,null).then().statusCode(200);
    }
}
