package com.jktest.company.wework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 * Description:读取该项目发送请求需要的配置（获取token需要的信息）
 */

//懒汉式单例模式
public class WeworkConfig {
    public String agentId;
    public String secret;
    public String corpid;
    public String contantSecret;
    public String baseUrl;

//    public String current;
//    public HashMap<String, HashMap<String, String>> env;

    private static  WeworkConfig weworkConfig;

    private WeworkConfig(){ }

    public static WeworkConfig getInstance() {
        if (weworkConfig == null) {
            weworkConfig=load("/config/WeworkConfig.yaml");
            /**
             * 不用new对象，通过读数据为对象赋值
             */
        }
        return weworkConfig;
    }

    public static WeworkConfig load(String path){
        // fixed:  read from yaml or json
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
           return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),WeworkConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
