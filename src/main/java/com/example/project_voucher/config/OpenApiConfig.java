package com.example.project_voucher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class OpenApiConfig {
    // http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public OpenAPI openApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Project Voucher API")
                        .version(LocalDateTime.now().toString())
                        .description("Project Voucher API"));

    }
}
