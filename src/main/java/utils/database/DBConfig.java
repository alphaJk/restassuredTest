package utils.database;

public class DBConfig {

    private static String username;
    private static String password;
    private static String url;
    private static boolean ddlRun;
    private static boolean ddlGenerate;

    public DBConfig() {
    }


    public  String getUsername() {
        return username;
    }

    public  void setUsername(String username) {
        DBConfig.username = username;
    }

    public  String getPassword() {
        return password;
    }

    public  void setPassword(String password) {
        DBConfig.password = password;
    }

    public  String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        DBConfig.url = url;
    }

    public static boolean isDdlRun() {
        return ddlRun;
    }

    public static void setDdlRun(boolean ddlRun) {
        DBConfig.ddlRun = ddlRun;
    }

    public static boolean isDdlGenerate() {
        return ddlGenerate;
    }

    public static void setDdlGenerate(boolean ddlGenerate) {
        DBConfig.ddlGenerate = ddlGenerate;
    }
}
