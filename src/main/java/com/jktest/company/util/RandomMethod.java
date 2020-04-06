package com.jktest.company.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class RandomMethod {


    //生成随机字符串
    public static String getRandomString(){
        String str =  RandomStringUtils.randomAlphanumeric(5);
        return str;
    }
    //生成随机手机号码
    public static String getTel() {
        String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    public static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }
}
