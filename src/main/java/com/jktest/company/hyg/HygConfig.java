package com.jktest.company.hyg;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jktest.company.wework.WeworkConfig;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class HygConfig {
    public String username;
    public String password;
    public String token="M3924!srLJ+Cql6Ovn1QYiawsVJGL1bMhIL1Zla31HEWLJ3Ny1XIJ25bGd4JN0D5AEOpjYWmR471neh8PrdjPr6IVR52dkS9On8s7x.507e888c6e425003beafd9be511957ff";
    public String baseUrl;
    private static HygConfig hygConfig;
    private HygConfig(){}

    public static HygConfig getInstance(){
        if(hygConfig == null)
            hygConfig = load("/config/HygConfig.yaml");
        return hygConfig;
    }


    public static HygConfig load(String path){
        // fixed:  read from yaml or json
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(WeworkConfig.class.getResourceAsStream(path),HygConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
