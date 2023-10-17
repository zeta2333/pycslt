package usts.pycro.pycslt.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import usts.pycro.pycslt.manager.properties.ApplicationProperties;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 15:41
 */
@SpringBootApplication
@ComponentScan({"usts.pycro.pycslt"})
@EnableConfigurationProperties(value = {ApplicationProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }
}
