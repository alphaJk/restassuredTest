package service.test.demo;

import api.Api;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-27
 * Time: 15:30
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ServiceDemo extends Api {
    private String host;
    private static ServiceDemo serviceDemo;

    public static ServiceDemo getInstance() {
        if (serviceDemo == null) {
            synchronized (ServiceDemo.class) {
                if (serviceDemo == null) {
                    serviceDemo = new ServiceDemo();
                }
            }
        }
        return serviceDemo;
    }

    public void init(String host){
        this.host = host;
    }

    /**
     * 初始化请求对象
     * @return requestSpecification
     */
    @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification.baseUri(host);
        return requestSpecification;
    }

    /**
     * 测试接口1
     * @param uid
     * @param did
     * @param region
     * @param file_id
     * @return
     */
    public Response jkTest1(String uid,String did,String region,String file_id){
        Map<String,Object> params = new HashMap<>();
        params.put("uid",uid);
        params.put("did",did);
        params.put("region",region);
        if (StringUtils.isNotEmpty(file_id))
            params.put("file_id",file_id);
        return getResponseFromYaml("requestData/test.yaml",params,null);
    }


    /**
     * 测试接口2
     * @param productKey
     * @param userId
     * @param token
     * @param client_id
     * @return
     */
    public Response jkTest2(String productKey,String userId,String token,String client_id){
        Map<String,Object> params = new HashMap<>();
        params.put("productKey",productKey);
        params.put("userId",userId);
        params.put("token",token);
        if (StringUtils.isNotEmpty(client_id))
            params.put("client_id",client_id);
        Map<String,String> headers = new HashMap<>();
        headers.put("sign","1111111");
        return getResponseFromYaml("requestData/test2.yaml",params,headers);
    }

    /**
     * 测试接口3
     * @param json
     * @return
     */
    public Response jkTest3(JSONObject json){
        Map<String,String> headers = new HashMap<>();
        headers.put("sign","1111111");
        return getResponseFromYaml("requestData/test3.yaml",json,headers);
    }

    /**
     *
     * @return
     */
    public Response jkTest4(){
        Map<String,String> headers = new HashMap<>();
        headers.put("sign","1111111");
        Map<String,Object> params = new HashMap<>();
        params.put("arg1","arg1");
        params.put("arg2","arg2");
        return getResponseFromYaml("requestData/test4.yaml",params,headers);
    }

    public Response jkTest5(){
        Map<String,Object> params = new HashMap<>();
        params.put("arg1","arg1");
        params.put("arg2","arg2");
        return getResponseFromYaml("requestData/test5.yaml",params,null);
    }

    public Response jkTest6(){
        Map<String,Object> params = new HashMap<>();
        params.put("arg1","arg1");
        params.put("arg2","arg2");
        return getResponseFromYaml("requestData/test6.yaml",params,null);
    }

}
