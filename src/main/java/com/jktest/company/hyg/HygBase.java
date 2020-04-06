package com.jktest.company.hyg;

import com.jktest.company.Api;
import com.jktest.company.wework.Wework;
import com.jktest.company.wework.WeworkConfig;
import io.restassured.specification.RequestSpecification;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class HygBase extends Api {
    @Override
    public RequestSpecification getDefaultRequestSpecification(){
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification
//                .proxy("192.168.3.7",8888)
                // todo 设置测试环境 在Api类中操作
//                .baseUri(WeworkConfig.getInstance().baseUrl)
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .formParam("token",Hyg.getToken());
        return requestSpecification;
    }

}
