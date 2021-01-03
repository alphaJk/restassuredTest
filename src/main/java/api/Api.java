package api;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
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

    private static Logger log = LoggerFactory.getLogger(Api.class);
    public Api(){
        useRelaxedHTTPSValidation();
        requestSpecification =  given().log().all();
    }

    /**
     * json模板方法，传入map，设定json数据
     * @param path
     * @param map
     * @return
     */
    static String template(String path, Map<String, Object> map){
        try {
            DocumentContext documentContext = JsonPath.parse(Api.class.getResourceAsStream(path));
            if (ObjectUtil.isNotNull(map)) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    documentContext.set(entry.getKey(), entry.getValue());
                }
                return documentContext.jsonString();
            }
            return  documentContext.jsonString();
        }catch (Exception e){
            e.printStackTrace();
            log.error("读取json文件出错");
            return null;
        }
    }

    /**
     * 从har中读取请求，更新restful对象的成员变量
     * @param path
     * @param pattern 正则匹配的需要的内容
     * @return
     */
    private Restful getApiDataFromHar(String path, String pattern){
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(
                    URLDecoder.decode(
                            getClass().getResource(path).getPath(), "utf-8"
                    )));
            HarRequest request = new HarRequest();
            Boolean match=false;
            //通过传进来的pattern，正则匹配包含需要的url的request数据
            for (HarEntry entry : har.getLog().getEntries()) {
                request = entry.getRequest();
                if (request.getUrl().matches(pattern)) {
                    match=true;
                    break;
                }
            }

            if(match==false){
                request=null;
                throw new Exception("没有匹配");
            }

            Restful restful = new Restful();
            //初始化restful的信息
            restful.method = request.getMethod().name().toLowerCase();
            //fix  去掉url中的query部分
            String regex = "(\\?)(.*)";
            String replaceStr = "";
            //正则配出的字段从原url中替换成replaceStr
            String newUrl = request.getUrl().replaceAll(regex,replaceStr);
            //fix 多环境支持，删除域名（或者服务器地址端口号）
            String check = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*";
            restful.url = newUrl.replaceAll(check,replaceStr);


            //判断请求类型
            if (restful.method.contains("get")) {
                //获取请求参数
                restful.query = new HashMap<>();
                for (HarQueryParam q : request.getQueryString()) {
                    restful.query.put(q.getName(), q.getValue());
                }
                return restful;
            }
            //fixed 判断formdata和json  x-www-form-urlencoded类型请求
            if (request.getPostData().getMimeType().contains("x-www-form-urlencoded")){
                //获取x-www-form-urlencoded的请求参数
                List<HarPostDataParam> testObj = request.getPostData().getParams();
                restful.formParams = new HashMap<>();
                for (HarPostDataParam p:testObj)
                    restful.formParams.put(p.getName(), p.getValue());
            }else {
                //获取JSON请求参数
                restful.body = request.getPostData().getText();
            }
            return restful;

        } catch (HarReaderException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从yaml文件中读取Restful的基本请求信息（url,method,header,query等
     * @param path
     * @return
     */
    Restful getApiDataFromYaml(String path){
        Yaml yaml = new Yaml();
        try{
            InputStream in = YamlReader.class.getClassLoader().getResourceAsStream(path);
            Restful restful  = yaml.loadAs(in, Restful.class);
            return restful;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取request-yaml文件出错");
            return null;
        }
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
            restful.body = template(filePath, map);
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
    protected void defaultRequestSpecificationInit(String host) {
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
                //响应时间大于2秒就报错
                .time(lessThan(4000L)).extract().response();
    }

    public Response methodPost(String uri, Map<String,String> headers, Map<String,String> form){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (ObjectUtil.isNotNull(form))
            requestSpecification.formParams(form);
        return requestSpecification
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .request("post",uri)
                .then().log().all()
                .time(lessThan(4000L))
                .extract()
                .response();
    }

    public Response methodPostJson(String uri, Map<String,String> headers, String jsonString){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (StrUtil.isNotEmpty(jsonString))
            requestSpecification.body(jsonString);
        return requestSpecification
                .contentType(ContentType.JSON)
                .request("post",uri)
                .then().log().all()
                .time(lessThan(4000L))
                .extract()
                .response();

    }

    public Response methodGet(String uri, Map<String,String> headers, Map<String,String> form){
        if (ObjectUtil.isNotNull(headers))
            requestSpecification.headers(headers);
        if (ObjectUtil.isNotNull(form))
            requestSpecification.params(form);
        return requestSpecification
                .contentType(ContentType.ANY)
                .request("get",uri)
                .then().log().all()
                .time(lessThan(4000L))
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
        Restful restful= getApiDataFromYaml(path);
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
        Restful restful= getApiDataFromHar(path,pattern);
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
