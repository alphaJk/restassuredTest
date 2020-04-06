package com.jktest.company;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 * Description:定义接口的参数(接口请求包含的数据)
 */
public class Restful {
    public String url;
    public String method;
    public HashMap<String,String> headers;
    //get请求
    public HashMap<String,String> query;
    //form-data-post
    public HashMap<String,String> params;
    //json-post
    public String  body;
}
