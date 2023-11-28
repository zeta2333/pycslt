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
    private String alipayUrl;
    private String appPrivateKey;
    public String alipayPublicKey;
    private String appId;
    public String returnPaymentUrl;
    public String notifyPaymentUrl;

    public final static String format = "json";
    public final static String charset = "utf-8";
    public final static String sign_type = "RSA2";
}
