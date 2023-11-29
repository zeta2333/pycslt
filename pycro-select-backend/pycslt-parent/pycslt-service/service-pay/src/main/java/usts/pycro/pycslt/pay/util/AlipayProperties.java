package usts.pycro.pycslt.pay.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-28 11:37
 */
@Data
@ConfigurationProperties(prefix = "app.alipay")
public class AlipayProperties {
    public final static String format = "json";
    public final static String charset = "utf-8";
    public final static String sign_type = "RSA2";
    public String alipayPublicKey;
    public String returnPaymentUrl;
    public String notifyPaymentUrl;
    private String alipayUrl;
    private String appPrivateKey;
    private String appId;
}
