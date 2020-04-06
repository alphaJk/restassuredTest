package com.jktest.company.wework;

import io.restassured.RestAssured;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 * Description:获取token
 */

//懒汉式单例模式
public class Wework {

    private static String token;
    private Wework(){}

    public static String getToken(){
        // TODO:  支持两种类型的token
        if (token == null){
            token = getWewokToken(WeworkConfig.getInstance().contantSecret);
        }
        return token;
    }


    public static String getWewokToken(String secret){
        return RestAssured.given()
                .queryParam("corpid", WeworkConfig.getInstance().corpid)
                .queryParam("corpsecret", secret)
                .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all().statusCode(200)
                .extract().path("access_token");
    }
}
