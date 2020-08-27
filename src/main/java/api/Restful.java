package api;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-26
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Data
public class Restful {
    public String url;
    public String method;
    public Map<String,String> headers;
    //get请求
    public HashMap<String,String> query;
    //form-data-post
    public HashMap<String,String> params;
    //json-post
    public String  body;
}
