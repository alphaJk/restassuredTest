package api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import exception.BizException;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Utils;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-01-22
 * Time: 17:04
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class RestAssuredHttpHandleBuilder {
    private volatile static RestAssuredHttpHandleBuilder builder = null;
    private static final Long LESS_TIME = 3000L;
    private static final int CODE_200 = 200;


    public static RestAssuredHttpHandleBuilder create() {
        if (builder == null) {
            synchronized (RestAssuredHttpHandleBuilder.class) {
                if (builder == null) {
                    builder = new RestAssuredHttpHandleBuilder();
                }
            }
        }
        return builder;
    }

    private RestAssuredHttpHandleBuilder(){
    }

    public RestAssuredHttpHandleBuilder setBaseUrl(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        return this;
    }


    /**
     * delete 默认方法
     * @param headers
     * @param parametersMap
     * @param uri
     * @return
     */
    public Response delete(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri){
        return delete(headers,parametersMap,uri,null);
    }

    /**
     * @param headers
     * @param parametersMap
     * @param uri
     * @param filters
     * @return
     */
    public Response delete(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        paramsCheck(httpClient,headers,uri,filters);
        uriTypeCheck(httpClient,parametersMap,uri);
        return httpClient
                .request(Method.DELETE,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }


    /**
     * get请求 默认方法
     * @param headers
     * @param parametersMap
     * @param uri
     * @return
     */
    public Response get(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri){
        return get(headers,parametersMap,uri,null);
    }

    /**
     *
     * @param headers
     * @param parametersMap
     * @param uri
     * @param filters
     * @return
     */
    public Response get(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        paramsCheck(httpClient,headers,uri,filters);
        uriTypeCheck(httpClient,parametersMap,uri);
        return httpClient
                .request(Method.GET,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    /**
     * post请求 json请求,有过滤器
     * @param headers
     * @param data
     * @param uri
     * @return
     */
    public Response post(Map<String, ?> headers, String data, String uri,Filter...filters) {
        return post(headers, data, ContentType.JSON,uri,filters);
    }

    /**
     * post请求 默认json请求,无过滤器
     * @param headers
     * @param data
     * @param uri
     * @return
     */
    public Response post(Map<String, ?> headers, String data, String uri) {
        return post(headers, data, ContentType.JSON,uri,null);
    }

    public Response post(Map<String, ?> headers, String data,ContentType contentType, String uri){
        return post(headers,data,contentType,uri,null);
    }

    /**
     * post请求，入参为json,xml,text
     * @param headers
     * @param data
     * @param contentType
     * @param uri
     * @param filters 过滤器
     * @return
     */
    public Response post(Map<String, ?> headers, String data, ContentType contentType, String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        //判断contentType
        if (contentType != ContentType.JSON && contentType != ContentType.XML && contentType != ContentType.TEXT ) {
            throw new BizException("content-type错误,只支持JSON,XML,TEXT");
        }
        paramsCheck(httpClient,headers,uri,filters);
        if (StrUtil.isNotBlank(data))
            httpClient.body(data);
        return httpClient
                .contentType(contentType.withCharset("UTF-8"))
                .request(Method.POST,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    /**
     * post请求 默认表单模式
     * @param headers
     * @param parametersMap
     * @param uri
     * @return
     */
    public Response post(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri){
        return post(headers,parametersMap,uri,null);
    }

    /**
     * post 请求表单模式
     * @param headers
     * @param parametersMap
     * @param uri
     * @param filters
     * @return
     */
    public Response post(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        paramsCheck(httpClient,headers,uri,filters);
        httpClient.contentType(ContentType.URLENC.withCharset("UTF-8"));
        uriTypeCheck(httpClient,parametersMap,uri);
        return httpClient
                .request(Method.POST,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    /**
     * post请求 json请求,有过滤器
     * @param headers
     * @param data
     * @param uri
     * @return
     */
    public Response put(Map<String, ?> headers, String data, String uri,Filter...filters) {
        return put(headers, data, ContentType.JSON,uri,filters);
    }

    /**
     * post请求 默认json请求,无过滤器
     * @param headers
     * @param data
     * @param uri
     * @return
     */
    public Response put(Map<String, ?> headers, String data, String uri) {
        return put(headers, data, ContentType.JSON,uri,null);
    }

    public Response put(Map<String, ?> headers, String data,ContentType contentType, String uri){
        return put(headers,data,contentType,uri,null);
    }

    /**
     * post请求，入参为json,xml,text
     * @param headers
     * @param data
     * @param contentType
     * @param uri
     * @param filters 过滤器
     * @return
     */
    public Response put(Map<String, ?> headers, String data, ContentType contentType, String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        //判断contentType
        if (contentType != ContentType.JSON && contentType != ContentType.XML && contentType != ContentType.TEXT ) {
            throw new BizException("content-type错误,只支持JSON,XML,TEXT");
        }
        paramsCheck(httpClient,headers,uri,filters);
        if (StrUtil.isNotBlank(data))
            httpClient.body(data);
        return httpClient
                .contentType(contentType.withCharset("UTF-8"))
                .request(Method.PUT,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    /**
     * put请求 默认表单模式
     * @param headers
     * @param parametersMap
     * @param uri
     * @return
     */
    public Response put(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri){
        return put(headers,parametersMap,uri,null);
    }

    /**
     * put 请求表单模式
     * @param headers
     * @param parametersMap
     * @param uri
     * @param filters
     * @return
     */
    public Response put(Map<String, ?> headers, Map<String, ?> parametersMap ,String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        paramsCheck(httpClient,headers,uri,filters);
        httpClient.contentType(ContentType.URLENC.withCharset("UTF-8"));
        uriTypeCheck(httpClient,parametersMap,uri);
        return httpClient
                .request(Method.PUT,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    /**
     * post请求 默认文件上传
     * @param headers
     * @param path
     * @param uri
     * @return
     */
    public Response postMultipart(Map<String, ?>headers,String path,String uri){
        return postMultipart(headers,path,uri,null);
    }

    /**
     *  post请求 文件上传
     * @param headers
     * @param path
     * @param uri
     * @param filters
     * @return
     */
    public Response postMultipart(Map<String, ?> headers, String path,String uri, Filter...filters){
        RequestSpecification httpClient =  given().log().all();
        paramsCheck(httpClient,headers,uri,filters);
        if (!StrUtil.isNotBlank(path)){
            throw new BizException("请传入文件路径");
        }
        return httpClient
                .contentType("multipart/form-data")
                .multiPart(new File(path))
                .request(Method.POST,uri)
                .then().statusCode(CODE_200)
                .log().status().log().body()
                .time(lessThan(LESS_TIME)).extract().response();
    }

    private void paramsCheck(RequestSpecification httpClient,Map<String, ?> headers,String uri,Filter...filters){
        if (ObjectUtil.isNotNull(filters) && filters.length>0)
            httpClient.filters(Arrays.asList(filters));
        if (ObjectUtil.isNotNull(headers))
            httpClient.headers(headers);
        if (!StrUtil.isNotBlank(uri))
            throw new BizException("uri不能为空");
    }



    private void uriTypeCheck(RequestSpecification httpClient, Map<String, ?> parametersMap, String uri){
        if (ObjectUtil.isNotNull(parametersMap)){
            if (Utils.isExistBrace(uri)){
                httpClient.pathParams(parametersMap);
            }else {
                httpClient.formParams(parametersMap);
            }
        }
    }
}
