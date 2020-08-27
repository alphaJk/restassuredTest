package demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("get请求")
    void jkTest2(){
        api.jkTest2("111","222","333","file_id_test").then().statusCode(200);
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
}