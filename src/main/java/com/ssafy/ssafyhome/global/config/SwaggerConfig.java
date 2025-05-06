package com.ssafy.ssafyhome.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "ssafyHome API 명세서", version = "v1"))
public class SwaggerConfig {
    @Bean
    GroupedOpenApi memberOpenApi() {
        String[] paths = { "/api/member/**", "/api/auth/**" };
        return GroupedOpenApi.builder().group("Member 관련 API").pathsToMatch(paths).build();
    }

    @Bean
    GroupedOpenApi houseDealOpenApi() {
        String[] paths = { "/api/house/**" };
        return GroupedOpenApi.builder().group("거래 조회 관련 API").pathsToMatch(paths).build();
    }

    @Bean
    GroupedOpenApi dongCodeOpenApi() {
        String[] paths = { "/api/dongcode/**" };
        return GroupedOpenApi.builder().group("주소 관련 API").pathsToMatch(paths).build();
    }

}
