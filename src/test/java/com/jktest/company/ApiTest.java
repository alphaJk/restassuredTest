package com.jktest.company;

import org.junit.jupiter.api.Test;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 14:38
 * To change this template use File | Settings | File Templates.
 * Description:
 */
class ApiTest {

    @Test
    void getResponesFromHar() {
        Api api = new Api();
//        api.getResponesFromHar("")
    }
    @Test
    void getUrl(){
        String url = "http://yushu.talelin.com/book/search?q=%E6%B5%8B%E8%AF%95";
        String pattern = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+(:[0-9]+\\/)([A-Za-z0-9]+)";
        String sPath = url.replaceFirst(pattern,"");
        System.out.println(sPath);
    }
}