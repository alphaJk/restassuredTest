package api;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import lombok.extern.slf4j.Slf4j;
import utils.JSONSerializerUtil;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-02-20
 * Time: 16:28
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Slf4j
public class AllureRestAssuredLogFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        Response response = ctx.next(requestSpec, responseSpec);

        String url = requestSpec.getURI();
        String method = requestSpec.getMethod();
        Headers headers = requestSpec.getHeaders();

        Map reqFormParams =  requestSpec.getFormParams();
        String requestsStr = reqFormParams != null && reqFormParams.size() > 0 ? JSONSerializerUtil.serialize(reqFormParams)
                : JSONSerializerUtil.serialize(requestSpec.getBody());

        String requestData = String.format("%s\n%s\n%s\n%s\n",url,method,headers,requestsStr);
        String responseData = String.format("%s\n%s\n",response.getStatusCode(),response.asString());
        //日志发送到Allure报告中
        Allure.addAttachment("接口请求日志", requestData);
        Allure.addAttachment("接口响应日志", responseData);
        return response;
    }
}
