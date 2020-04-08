package com.jktest.company.hyg;

import com.jktest.company.Api;
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
                // fix 多环境支持
                .baseUri(HygConfig.getInstance().baseUrl)
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .formParam("token",Hyg.getToken());
        return requestSpecification;
    }

}
