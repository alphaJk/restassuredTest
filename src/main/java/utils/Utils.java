package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2021-01-26
 * Time: 16:09
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class Utils {

    public static boolean isExistBrace(String input) {
        String regex = "\\{([^}]*)\\}";
        Pattern pattern = compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }
}
