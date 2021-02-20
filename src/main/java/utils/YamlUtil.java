package utils;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-12-07
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class YamlUtil {
    public YamlUtil() {
    }

    public static Map<String, Object> load(InputStream stream) {
        Yaml yaml = new Yaml();
        return (Map)yaml.load(stream);
    }

    public static Map<String, Object> loadFile(String filePath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        return (Map)yaml.load(new FileInputStream(filePath));
    }

    public static Iterable<Object> loadAll(String filePath) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        return yaml.loadAll(new FileInputStream(filePath));
    }

    public static <T> T loadFile(String filePath, Class<T> clz) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        return yaml.loadAs(new FileInputStream(filePath), clz);
    }

    public static void dump(Map<String, Object> params, String path) throws IOException {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        yaml.dump(params, new FileWriter(path));
    }
}
