package demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.given;
/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-27
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class ServiceDemoTest extends BaseTest{

    @Test
    @DisplayName("post请求form-data格式")
    void jkTest1(){
        api.jkTest1("111","222","333","file_id_test").then().statusCode(200);
    }

    @Test
    @DisplayName("可选参数post请求form-data格式")
    void jkTest11(){
        api.jkTest1("111","222",null,"file_id_test").then().statusCode(200);
    }

    @Test
    @DisplayName("get请求")
    void jkTest2(){
        api.jkTest2("111",null,"333","file_id_test").then().statusCode(200).body("code",equalTo(200));
    }

    @Test
    @DisplayName("可选参数-get请求")
    void jkTest22(){
        api.jkTest2("111",null,"333","file_id_test").then().statusCode(200).body("code",equalTo(200));
    }

    @Test
    @DisplayName("post请求json格式")
    void jkTest3(){
        JSONObject data = new JSONObject();
        data.put("deviceLists","111111");
        JSONObject json = new JSONObject();
        json.put("data",data);
        api.jkTest3(json).then().statusCode(200);
    }

    @Test
    @DisplayName("get-pathParams")
    void jkTest4(){
        api.jkTest4().then().statusCode(200);
    }
    @Test
    @DisplayName("post-pathParams")
    void jkTest5(){
        api.jkTest5().then().statusCode(200);
    }

    @Test
    @DisplayName("post-queryParams")
    void jkTes6(){
        api.jkTest6().then().statusCode(200);
    }

}