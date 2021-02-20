package service.SGB;

import api.Api;
import io.restassured.response.Response;
import service.test.demo.ServiceDemo;

import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-11-10
 * Time: 10:25
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ServiceSGBApi extends Api {
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
        setBaseUri(host);
    }

    public Response getVersion(){
        return getResponseFromYaml("requestData/SGB/version.yaml",null,null);
    }

    /**
     * 向GB28181接入网关订阅消息
     * @param params
     * @return
     */
    public Response getSubscribe(Map<String,Object> params){
        return  getResponseFromYaml("requestData/SGB/subscribe.yaml",params,null);
    }

    /**
     * 刷新订阅
     * @param params
     * @return
     */
    public Response refreshSubscribe(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/refreshSubscribe.yaml",params,null);
    }

    /**
     * 取消已有的订阅
     * @param params
     * @return
     */
    public Response unsubscribe(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/unsubscribe.yaml",params,null);

    }
    /**
     * 接入平台的信息查询
     * @return
     */
    public Response getDeviceList(){
        return getResponseFromYaml("requestData/SGB/getDeviceList.yaml",null,null);
    }

    /**
     * 查询平台的组织设备信息
     * @param params
     * @return
     */
    public Response catalog(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/catalog.yaml",params,null);
    }

    /**
     * 获取组织设备的信息
     * @param params
     * @return
     */
    public Response getDeviceInfo(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/deviceInfo.yaml",params,null);
    }

    /**
     * 获取组织设备的状态
     * @param params
     * @return
     */
    public Response getDeviceStatus(Map<String,Object> params) {
        return getResponseFromYaml("requestData/SGB/deviceStatus.yaml", params, null);
    }

    /**
     * 设备录像查询
     * @param params
     * @return
     */
    public Response getRecordInfo(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/recordInfo.yaml", params, null);
    }

    /**
     * ptz控制
     * @param params
     * @return
     */
    public Response ptzCmd(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/ptzCmd.yaml", params, null);
    }

    /**
     * 直播、回放、录像下载
     * @param params
     * @return
     */
    public Response awaken(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/awaken.yaml", params, null);
    }

    public Response mediaStop(Map<String,Object> params){
        return getResponseFromYaml("requestData/SGB/mediaStop.yaml", params, null);
    }
}
