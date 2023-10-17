package usts.pycro.pycslt.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-17 16:28
 */
@Data
@ConfigurationProperties(prefix = "app.auth")
public class ApplicationProperties {

    private List<String> noAuthUrls;

}
