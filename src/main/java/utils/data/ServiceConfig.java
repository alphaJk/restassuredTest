package utils.data;

import utils.YamlUtil;
import api.Api;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-12-07
 * Time: 14:51
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ServiceConfig {

    private Map<String, Object> profile;
    private String region;


    private ServiceConfig(Map<String, Object> profile) {
        this.profile = profile;
    }

    public static ServiceConfig load(String path){
        try {
            Map<String, Object> result = YamlUtil.loadFile(path);
            return new ServiceConfig(result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        ServiceConfig serviceConfig = ServiceConfig.load(Objects.requireNonNull(Api.class.getClassLoader().getResource("config.yaml")).getPath());
        String currentServer = serviceConfig.profile.get("currentServer").toString();
        Map<String, Map<String,Objects>> hosts = (Map)serviceConfig.profile.get("毕方");
        System.out.println(hosts.get("esd").get(currentServer));


    }
}
