package api;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-26
 * Time: 17:09
 * To change this template use File | Settings | File Templates.
 * Description:
 */
/*public class ServiceConfig {

    public Map<String,String> sign;
    public Map<String,String> login;
    public Map<String,Map<String,String>> env_bf;
    public Map<String,Map<String,String>> env_zq;
    public Map<String,Map<String,String>> env_blurams;
    public Map<String,Map<String,String>> cloudlogin;
    public Map<String,Map<String,String>> zq_cloudlogin;
    public Map<String,Map<String,String>> bl_cloudlogin;

    public String region;
    //环境设置

    public String currentServer;

    private static ServiceConfig Config;

    private ServiceConfig(){}

    public static ServiceConfig getInstance(){
        if(Config == null)
            Config = load("/config/config.yaml");
        return Config;
    }


    public static ServiceConfig load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(ServiceConfig.class.getResourceAsStream(path), ServiceConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

   *//* *//**//**
     * 获取sign验证的地址
     * @return
     *//**//*
    public String getSignUrl(){
        return Config.sign.get("url").toString();
    }

    *//**//**
     * 获取sign用户名
     * @return
     *//**//*
    public String getSignUsername(){
        return Config.sign.get("username").toString();
    }

    *//**//**
     * 获取sign密码
     * @return
     *//**//*
    public String getSignPassword(){
        return Config.sign.get("password");
    }

    public String getSignRegion(){
        return Config.region;
    }

    public String getLoginUrl(){
        return Config.login.get(currentServer);
    }

    public String getLoginUsername(){
        return Config.login.get("username");
    }

    public String getLoginPW(){
        return Config.login.get("password");
    }
    //毕方环境
    public String getBfEsd(){
        return Config.env_bf.get("esd").get(currentServer);
    }
    public String getBfcloud(){
        return Config.env_bf.get("cloud").get(currentServer);
    }
    public String getBfOpen(){
        return Config.env_bf.get("openapi").get(currentServer);
    }
    //朱雀环境
    public String getZqEsd(){
        return Config.env_zq.get("esd").get(currentServer);
    }
    public String getZqBms(){
        return Config.env_zq.get("bms").get(currentServer);
    }
    public String getZqFaceApi(){
        return Config.env_zq.get("faceapi").get(currentServer);
    }
    //Blurams
    public String getBluramsUpns(){
        return Config.env_blurams.get("upns").get(currentServer);
    }
    public String getBluramsEsd(){return Config.env_blurams.get("esd").get(currentServer);}
    public String getBluramsGms(){
        return Config.env_blurams.get("gms").get(currentServer);
    }
    public String getBluramsCloud(){
        return Config.env_blurams.get("cloud").get(currentServer);
    }
    public String getBluramsCoreData(){
        return Config.env_blurams.get("coredata").get(currentServer);
    }
    public String getBluramsSoul(){return Config.env_blurams.get("soul").get(currentServer);}
    //联通新零售
    public String getBmsUnicomRetail(){
        return Config.env_zq.get("bmsUnicomRetail").get(currentServer);
    }
    public String getEsdUnicomRetail(){
        return Config.env_zq.get("esdUnicomRetail").get(currentServer);
    }
    public String getSoulUnicomRetail(){
        return Config.env_zq.get("soul").get(currentServer);
    }

    //获取各种登录信息
    public String getCloudUserName(){
        return Config.cloudlogin.get(currentServer).get("username");
    }

    public String getCloudPW(){
        return Config.cloudlogin.get(currentServer).get("password");
    }

    //朱雀cloud登录信息
    public String getZqCloudUN(){
        return Config.zq_cloudlogin.get(currentServer).get("username");
    }
    public String getZqCloudPW(){
        return Config.zq_cloudlogin.get(currentServer).get("password");
    }

    //blurams cloud登录信息
    public String getBlCloudUN(){
        return Config.bl_cloudlogin.get(currentServer).get("username");
    }
    public String getBlCloudPW(){
        return Config.bl_cloudlogin.get(currentServer).get("password");
    }*//*
}*/
