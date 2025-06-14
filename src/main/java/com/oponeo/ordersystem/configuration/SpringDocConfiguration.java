package com.oponeo.ordersystem.configuration;

import com.oponeo.ordersystem.OrderSystemApplication;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("order-system")
                .pathsToMatch("/**")
                .packagesToScan(OrderSystemApplication.class.getPackageName())
                .build();
    }

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Oponeo Order System API")
                        .description("REST API for managing customers, products and orders")
                        .version("1.0.0"));
    }
}
