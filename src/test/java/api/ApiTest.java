package api;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.test.demo.ServiceDemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-26
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class ApiTest {
    static Api api;
    static ServiceDemo apiDemo;

    @BeforeAll
    public static void beforeAll(){
        api  = new Api();
        apiDemo = ServiceDemo.getInstance();
        apiDemo.init("http://127.0.0.1:8079");
    }

    @Test
    void demoTest(){
        Restful data = api.getApidataFromYaml("requestData/test.yaml");
        System.out.println("header参数--->"+data.getHeaders());
        System.out.println("post请求form-data参数--->"+data.getFormParams());
        System.out.println("post请求json格式--->"+data.getBody());
        System.out.println("请求类型--->"+data.getMethod());
        System.out.println("get请求参数--->"+data.getQuery());
        System.out.println("请求的url--->"+data.getUrl());

    }

    @Test
    void jsonUpdate(){
        Map<String,Object> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("11111");
        Map<String,Object> data = new HashMap<>();
        data.put("deviceIdList2",list);
        map.put("data",data);
        String body = Api.template("/requestData/deviceInfoGet.json",map);
        System.out.println(body);
    }

}