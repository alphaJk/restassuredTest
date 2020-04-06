package com.jktest.company.wework.contacts;

import io.restassured.response.Response;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 * Description:成员管理业务线
 */
public class MemberManage extends ContactBaseInfo {

    public Response create(HashMap<String,Object> map){
        return  super.getResponseFromYaml("/api/create_member.yaml",map);
    }
}
