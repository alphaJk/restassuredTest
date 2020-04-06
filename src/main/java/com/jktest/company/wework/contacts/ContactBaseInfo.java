package com.jktest.company.wework.contacts;

import com.jktest.company.Api;
import com.jktest.company.wework.Wework;

import io.restassured.specification.RequestSpecification;


/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 * Description:创建不同业务需要的request信息
 */
public class ContactBaseInfo extends Api {

    /**
     * json格式用.body()--formdata格式用.formParam()--get请求是.queryParam()
     */

    @Override
    public RequestSpecification getDefaultRequestSpecification(){
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification
//                .proxy("192.168.3.7",8888)
                .queryParam("access_token", Wework.getToken());
//                .formParam("token",Wework.hygToken);




        requestSpecification.filter( (req,res,ctx)->{
            //todo 追加新的东西
            return ctx.next(req,res);
        });
        return requestSpecification;
    }
















//    //生成随机字符串
//    public String getRandomString(){
//        String str =  RandomStringUtils.randomAlphanumeric(5);
//        return str;
//    }
//    //生成随机手机号码
//    public  String getTel() {
//          String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
//        int index=getNum(0,telFirst.length-1);
//        String first=telFirst[index];
//        String second=String.valueOf(getNum(1,888)+10000).substring(1);
//        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
//        return first+second+third;
//    }
//    public  int getNum(int start,int end) {
//        return (int)(Math.random()*(end-start+1)+start);
//    }


}
