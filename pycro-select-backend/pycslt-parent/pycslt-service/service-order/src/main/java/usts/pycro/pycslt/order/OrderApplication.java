package usts.pycro.pycslt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import usts.pycro.pycslt.common.annotation.EnableUserLoginAuthInterceptor;
import usts.pycro.pycslt.common.annotation.EnableUserTokenFeignInterceptor;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-27 10:57
 */
@SpringBootApplication
@EnableFeignClients({"usts.pycro.pycslt.feign"})
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
