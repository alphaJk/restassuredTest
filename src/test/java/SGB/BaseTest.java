package SGB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.SGB.ServiceSGBApi;
import service.test.demo.ServiceDemo;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-08-27
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class BaseTest {
    static ServiceSGBApi api;

    @BeforeAll
    public static void prepare(){
        api = ServiceSGBApi.getInstance();
        api.init("http://172.26.13.89:8090");
    }



}
