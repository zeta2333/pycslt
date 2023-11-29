package usts.pycro.pycslt.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import usts.pycro.pycslt.common.annotation.EnableUserLoginAuthInterceptor;

/**
 * @author Pycro
 * @version 1.0
 * 2023-11-24 14:15
 */

// 排除数据库的自动化配置，Cart微服务不需要访问数据库
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients({"usts.pycro.pycslt.feign"})
@EnableUserLoginAuthInterceptor // 用户相关模块，需要登录状态
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

}
