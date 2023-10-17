package usts.pycro.pycslt.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-16 13:51
 * 测试地址：http://localhost:6815/doc.html
 */
@Configuration
public class Knife4jConfig {

    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称
                .pathsToMatch("/admin/**")  // 接口请求路径规则
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("PYCSLT-API接口文档")
                        .version("1.0")
                        .description("PYCSLT-API接口文档")
                        .contact(new Contact()
                                .name("Pycro")
                                .email("pycro6815@163.com")
                                .url("https://github.com/zeta2333"))); // 设定作者
    }

}