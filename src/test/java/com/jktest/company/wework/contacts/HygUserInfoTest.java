package com.jktest.company.wework.contacts;

import com.jktest.company.hyg.HygUserInfo;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class HygUserInfoTest {

    @Test
    void search() {
        HygUserInfo hygUserInfo = new HygUserInfo();
        HashMap<String,Object> map = new HashMap<>();
        map.put("beginYear","1991");
        hygUserInfo.search(map);
    }
}