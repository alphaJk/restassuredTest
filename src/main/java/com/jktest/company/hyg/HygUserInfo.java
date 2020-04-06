package com.jktest.company.hyg;

import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class HygUserInfo extends HygBase {
    public Response search(HashMap<String,Object> map){
        return super.getResponseFromYaml("/api/hyg.yaml",map);

    }

    public Response testHar(String path,String pattern,HashMap<String,Object> map){
        return super.getResponesFromHar(path,pattern,map);
    }
}
