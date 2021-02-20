package utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-12-08
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class HttpUtil {

    public static String generateSign(String accessKey, String requestTime,
                                       String uri, String sk, String params) {
        StringBuffer stringBuffer = new StringBuffer(1024);
        stringBuffer.append(accessKey);
        stringBuffer.append("|");
        stringBuffer.append(requestTime);
        stringBuffer.append("|");
        stringBuffer.append(uri);
        //第一步根据时间戳做一次加密
        HmacUtils hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, stringBuffer.toString());
        byte[] kDate = hmac.hmac(sk);
        //原始基数
        byte[] messageType = DigestUtils.sha256(params);
        //加密后的sk再对数据源做一层加密
        hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, kDate);
        return hmac.hmacHex(messageType);

    }
}
