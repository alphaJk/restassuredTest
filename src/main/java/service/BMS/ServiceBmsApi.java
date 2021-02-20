package service.BMS;

import utils.HttpUtil;
import api.Api;
import com.vaasplus.bms.model.req.ClientInfo;
import io.restassured.response.Response;
import service.test.demo.ServiceDemo;

import java.util.HashMap;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-12-08
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ServiceBmsApi extends Api {
    private static ServiceBmsApi serviceBmsApi;

    public static ServiceBmsApi getInstance() {
        if (serviceBmsApi == null) {
            synchronized (ServiceDemo.class) {
                if (serviceBmsApi == null) {
                    serviceBmsApi = new ServiceBmsApi();
                }
            }
        }
        return serviceBmsApi;
    }

    public void init(String host){
        setBaseUri(host);
    }


    public Response delPollinges(String pollingId,String cameraId,String token,String pk){
        String uri  = "/api/v1/pollinges/"+pollingId+"/cameras/"+cameraId;
        String sk = "3CpNGOkDe7iB313pdFdR";
        Long requestTime = System.currentTimeMillis();
        Map<String,Object> params  = new HashMap<>();
        params.put("pollingId",pollingId);
        params.put("cameraId",cameraId);
        Map<String,String> headers  = new HashMap<>();
        headers.put("Accept-AccessKey",pk);
        headers.put("Accept-Time",String.valueOf(requestTime));
        String sign = HttpUtil.generateSign(pk,String.valueOf(requestTime),uri,sk,params.toString());
        headers.put("Accept-Sign",sign);
        headers.put("token",token);
        return getResponseFromYaml("requestData/BMS/delPollinges.yaml", params, headers);
    }

    public Response login(String username, String pwd, ClientInfo clientInfo, String pk,String orgName){
        String uri  = "/api/v1/users/sessions";
        String sk = "3CpNGOkDe7iB313pdFdR";
        Long requestTime = System.currentTimeMillis();
        Map<String,Object> params  = new HashMap<>();
        params.put("username",username);
        params.put("pwd",pwd);
        params.put("clientInfo",clientInfo);
        params.put("productKey",pk);
        params.put("orgName",orgName);
        Map<String,String> headers  = new HashMap<>();
        headers.put("Accept-AccessKey",pk);
        headers.put("Accept-Time",String.valueOf(requestTime));
        String sign = HttpUtil.generateSign(pk,String.valueOf(requestTime),uri,sk,params.toString());
        headers.put("Accept-Sign",sign);
        return getResponseFromYaml("requestData/BMS/bmslogin.yaml", params, headers);
    }

}
