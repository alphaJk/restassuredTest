package Utils.data;

import com.alibaba.fastjson.annotation.JSONField;
import com.vaasplus.bms.model.enums.RequestSource;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: jk
 * Date: 2020-12-08
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class ClientInfo implements Serializable {
    String id;
    String clientType;
    String clientVersion;
    String clientInfo;
    String systemType;
    String systemVersion;
    String systemInfo;

    @JSONField(
            serialize = false
    )
    public boolean isValid() {
        return RequestSource.gbrs.name().equals(this.clientType) || RequestSource.retail.name().equals(this.clientType) || StringUtils.isNotBlank(this.clientType) && StringUtils.isNotBlank(this.clientVersion) && StringUtils.isNotBlank(this.systemVersion);
    }

    public ClientInfo() {
        this.clientType = RequestSource.web.name();
    }

    public String getId() {
        return this.id;
    }

    public String getClientType() {
        return this.clientType;
    }

    public String getClientVersion() {
        return this.clientVersion;
    }

    public String getClientInfo() {
        return this.clientInfo;
    }

    public String getSystemType() {
        return this.systemType;
    }

    public String getSystemVersion() {
        return this.systemVersion;
    }

    public String getSystemInfo() {
        return this.systemInfo;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setClientType(final String clientType) {
        this.clientType = clientType;
    }

    public void setClientVersion(final String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public void setClientInfo(final String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setSystemType(final String systemType) {
        this.systemType = systemType;
    }

    public void setSystemVersion(final String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public void setSystemInfo(final String systemInfo) {
        this.systemInfo = systemInfo;
    }

}
