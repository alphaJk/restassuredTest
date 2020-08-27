package demo;

import org.junit.jupiter.api.BeforeAll;
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
    static ServiceDemo api;

    @BeforeAll
    public static void prepare(){
        api = ServiceDemo.getInstance();
        api.init("http://127.0.0.1:8079");
    }
}
