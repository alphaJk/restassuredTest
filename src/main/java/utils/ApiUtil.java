package utils;

import api.Api;
import api.Restful;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.*;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-01-14
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ApiUtil {


    private static final Log log = LogFactory.get();
    public ApiUtil(){}


    /**
     * json模板方法，传入map，设定json数据
     * @param path
     * @param map
     * @return
     */
    public static String template(String path, Map<String, Object> map){
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
     * 从yaml文件中读取Restful的基本请求信息（url,method,header,query等
     * @param path
     * @return
     */
    public static Restful getApiDataFromYaml(String path){
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
     * 从har中读取请求，更新restful对象的成员变量
     * @param path
     * @param pattern 正则匹配的需要的内容
     * @return
     */
    public static Restful getApiDataFromHar(String path, String pattern){
        HarReader harReader = new HarReader();
        try {
            Har har = harReader.readFromFile(new File(
                    URLDecoder.decode(
                            ApiUtil.class.getResource(path).getPath(), "utf-8"
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
}
