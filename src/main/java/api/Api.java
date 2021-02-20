package api;

import utils.ApiUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-26
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Api {
    private RequestSpecification requestSpecification;
    private static final long lessTime = 3000L;

    public Api(){
        useRelaxedHTTPSValidation();
        requestSpecification =  given().log().all();
    }


    /**
     * 更新请求的参数
     * @param restful
     * @param map
     */
    private Restful updateApiMap(Restful restful, Map<String, Object> map){
        if (ObjectUtil.isNull(map))
            return restful;

        //form-data请求数据
        if (ObjectUtil.isNotNull(restful.formParams)) {
            for (Map.Entry<String, Object> entry : map.entrySet())
                if (ObjectUtil.isNotNull(entry.getValue()))
                    restful.formParams.replace(entry.getKey(), entry.getValue().toString());
        }
        //queryParams请求数据
        if (ObjectUtil.isNotNull(restful.queryParams)) {
            for (Map.Entry<String, Object> entry : map.entrySet())
                if (ObjectUtil.isNotNull(entry.getValue()))
                    restful.queryParams.replace(entry.getKey(), entry.getValue().toString());
        }
        //pathParams请求数据
        if (ObjectUtil.isNotNull(restful.pathParams)){
            for (Map.Entry<String, Object> entry : map.entrySet())
                if (ObjectUtil.isNotNull(entry.getValue()))
                    restful.pathParams.replace(entry.getKey(), entry.getValue().toString());
        }
        //json数据类型
        if (ObjectUtil.isNotNull(restful.body)) {
            String filePath = restful.body;
            // 通过body读取json地址
            restful.body = ApiUtil.template(filePath, map);
        }
        return restful;
    }



    /**
     * 更新headers
     * @param restful
     * @param map
     * @param headers
     * @return
     */
    private Restful updateApiMap(Restful restful,Map<String,Object> map,Map<String,String> headers){
        if (ObjectUtil.isNotNull(headers))
            restful.headers=headers;
        return updateApiMap(restful,map);
    }

    /**
     * 初始化host
     * @param host
     */
    public void setBaseUri(String host) {
        requestSpecification.baseUri(host);
    }

    /**
     * 发送请求
     * @param restful
     * @return
     */
    private Response getResponseFromRestful(Restful restful){
        if (ObjectUtil.isNotNull(restful.headers))
            requestSpecification.headers(restful.headers);

        //判断请求提是否为空
        if (ObjectUtil.isNotNull(restful.formParams)){
            for (Map.Entry<String, String> entry : restful.formParams.entrySet())
                /*可选参数过滤*/
                if (StrUtil.isNotEmpty(entry.getValue()))
                    requestSpecification.formParam(entry.getKey(), entry.getValue());
            requestSpecification.contentType(ContentType.URLENC.withCharset("UTF-8"));
        }
        if (ObjectUtil.isNotNull(restful.queryParams)){
            for (Map.Entry<String, String> entry : restful.queryParams.entrySet())
                /*可选参数过滤*/
                if (StrUtil.isNotEmpty(entry.getValue()))
                    requestSpecification.queryParam(entry.getKey(), entry.getValue());
            requestSpecification.contentType(ContentType.URLENC.withCharset("UTF-8"));
        }

        if (ObjectUtil.isNotNull(restful.pathParams)){
            for (Map.Entry<String, String> entry : restful.pathParams.entrySet())
                /*可选参数过滤*/
                if (StrUtil.isNotEmpty(entry.getValue()))
                    requestSpecification.pathParam(entry.getKey(), entry.getValue());
            requestSpecification.contentType(ContentType.URLENC.withCharset("UTF-8"));
        }
        if (ObjectUtil.isNotNull(restful.body)){
            requestSpecification.body(restful.body).contentType(ContentType.JSON);
        }

        /* 请求接口，返回response */
        return requestSpecification.request(restful.method,restful.url)
                .then().log().all()
                //响应时间大于3秒就报错
                .time(lessThan(lessTime)).extract().response();
    }

    /**
     * sample request
     * @param uri
     * @param headers
     * @param form
     * @return
     */
    public Response methodPost(String uri, Map<String,String> headers, Map<String,String> form){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (ObjectUtil.isNotNull(form))
            requestSpecification.formParams(form);
        return requestSpecification
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .request("post",uri)
                .then().log().all()
                .time(lessThan(lessTime))
                .extract()
                .response();
    }

    /**
     * sample request
     * @param uri
     * @param headers
     * @param jsonString
     * @return
     */
    public Response methodPostJson(String uri, Map<String,String> headers, String jsonString){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (StrUtil.isNotEmpty(jsonString))
            requestSpecification.body(jsonString);
        return requestSpecification
                .contentType(ContentType.JSON)
                .request("post",uri)
                .then().log().all()
                .time(lessThan(lessTime))
                .extract()
                .response();

    }

    /**
     * sample request
     * @param uri
     * @param headers
     * @param form
     * @return
     */
    public Response methodGet(String uri, Map<String,String> headers, Map<String,String> form){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (ObjectUtil.isNotNull(form))
            requestSpecification.params(form);
        return requestSpecification
                .contentType(ContentType.ANY)
                .request("get",uri)
                .then().log().all()
                .time(lessThan(lessTime))
                .extract()
                .response();
    }

    /**
     * 执行请操作FromYaml
     * @param path
     * @param map
     * @return
     */
    protected Response getResponseFromYaml(String path, Map<String, Object> map, Map<String, String> headers){
        Restful restful= ApiUtil.getApiDataFromYaml(path);
        restful=updateApiMap(restful,map,headers);
        return getResponseFromRestful(restful);
    }


    /**
     * 执行请操作FromHar
     * @param path
     * @param pattern
     * @param map
     * @return
     */
    public Response getResponesFromHar(String path, String pattern, Map<String,Object> map){
        Restful restful= ApiUtil.getApiDataFromHar(path,pattern);
        restful=updateApiMap(restful,map);
        return getResponseFromRestful(restful);
    }




    /**
     * 支持从swagger自动生成接口定义并发送
     * @param path
     * @param pattern
     * @param map
     * @return
     */
    public Response templateFromSwagger(String path, String pattern, Map<String, Object> map) {
        //todo: 支持从swagger自动生成接口定义并发送
        //todo: 分析swagger codegen
        return null;
    }




}
