package usts.pycro.pycslt.manager.system.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 16:28
 */
@Data
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private Auth auth;
    private Minio minio;


    @Getter
    @Setter
    public static class Minio {
        private String endpointUrl;
        private String accessKey;
        private String secretKey;
        private String bucketName;
    }

    @Getter
    @Setter
    public static class Auth {
        private List<String> noAuthUrls;
    }
}
