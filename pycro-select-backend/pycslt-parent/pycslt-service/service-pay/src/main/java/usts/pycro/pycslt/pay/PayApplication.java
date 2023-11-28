package usts.pycro.pycslt.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import usts.pycro.pycslt.common.annotation.EnableUserLoginAuthInterceptor;
import usts.pycro.pycslt.pay.util.AlipayProperties;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-28 10:43
 */
@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients({"usts.pycro.pycslt.feign"})
@EnableConfigurationProperties({AlipayProperties.class})
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
