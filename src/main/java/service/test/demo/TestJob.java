package service.test.demo;

import api.Api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-01-14
 * Time: 14:27
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class TestJob extends Api {

    public void sampleRequestTest1(){
        Map<String,Object> params = new HashMap<>();
        params.put("arg1","arg1");
        params.put("arg2","arg2");
        String result = methodPost("/open/visioninsight/push",null,null).then().statusCode(200).extract().body().toString();
        System.out.println(result);
    }


    /*public void test1(){
        System.out.println(11111);
    }*/




}
