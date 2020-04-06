package com.jktest.company.hyg;

import com.jktest.company.wework.WeworkConfig;
import io.restassured.RestAssured;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Hyg {
    private static String token;
    private Hyg(){}

    public static String getToken (){
        if (token == null)
            token = HygConfig.getInstance().token;
//            token = login(HygConfig.getInstance().username,HygConfig.getInstance().password);
        System.out.println("获取token成功");
        return token;
    }


    private static String login(String username,String password){
        return RestAssured.given().log().all()
                .queryParam("userAccount", username)
                .queryParam("passwd", password)
                .when().post("http://116.62.70.7:8081/HongYuanMuseum/login/userLogin")
                .then().log().all().statusCode(200)
                .extract().path("token");

    }
}
