package service.SGB;

import api.Api;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import service.test.demo.ServiceDemo;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-11-10
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ServiceSGBApi extends Api {
    private String host;
    private static ServiceSGBApi serviceSGBApi;

    public static ServiceSGBApi getInstance() {
        if (serviceSGBApi == null) {
            synchronized (ServiceDemo.class) {
                if (serviceSGBApi == null) {
                    serviceSGBApi = new ServiceSGBApi();
                }
            }
        }
        return serviceSGBApi;
    }
    public void init(String host){
        this.host = host;
    }

    @Override
    protected RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification = super.getDefaultRequestSpecification();
        requestSpecification.baseUri(host);
        return requestSpecification;
    }

    public Response getVersion(){
        return getResponseFromYaml("requestData/SGB/version.yaml",null,null);
    }

    public Response getDeviceList(){
        return getResponseFromYaml("requestData/SGB/getDeviceList.yaml",null,null);
    }

    public Response catalog(String deviceId,String mode,String startNo){
        Map<String,Object> params = new HashMap<>();
        params.put("deviceId",deviceId);
        if (StrUtil.isNotEmpty(mode))
            params.put("mode",mode);
        if (StrUtil.isNotEmpty(startNo))
            params.put("startNo",startNo);
        return getResponseFromYaml("requestData/SGB/catalog.yaml",params,null);
    }
}
