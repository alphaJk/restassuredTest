package com.jktest.company.wework.contacts;

import com.jktest.company.util.RandomMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class MemberManageTest {

    static MemberManage memberManage;
    @BeforeEach
    void setUp() {
        memberManage = new MemberManage();
    }

    //数据驱动
    @ParameterizedTest
//    @ValueSource(strings = {"jk_","jk2_","jk3_" })
    @CsvSource({
            "jk_, 1",
            "jk2_ ,2",
            "jk3_, 3"
    })
    void create(String name,int rank) {
        String newName = name+ RandomMethod.getRandomString();
        HashMap<String,Object> map = new HashMap<String, Object>(){{
            put("userid",newName);
            put("name",newName);
            put("mobile",RandomMethod.getTel());
            put("department", Arrays.asList(1,2));
            put("email",newName+"@qq.com");
        }};
        memberManage.create(map).then().statusCode(200).body("errmsg",equalTo("created")).body("errmsg",equalTo("created"));
        System.out.println(rank+"++++++++++++++++++++++++++++++++++++");
    }
}