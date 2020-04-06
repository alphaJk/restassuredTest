package com.jktest.company.hyg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@DisplayName("HYG成员信息")
class HygUserInfoTest {
    HygUserInfo hygUserInfo;

    @BeforeEach
    void setUp(){
      hygUserInfo = new HygUserInfo();
    }

    @Test
    @DisplayName("搜索用户信息")
    void search() {

        HashMap<String,Object> map = new HashMap<>();
        map.put("beginYear","1991");
        hygUserInfo.search(map).then().statusCode(200);
    }

    @Test
    @DisplayName("通过HAR搜索用户信息")
    void testHar() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("registeredResidence","");
        map.put("dwellingPlace","");
        map.put("maritalStatus","");
        hygUserInfo.testHar("/api/web.har.json",".*getUserInfoByConditionalSearch.*",map);
    }
}