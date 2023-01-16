package com.example.apibasic.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 설정파일
@Configuration
public class SwaggerConfig {

    // xml 파일에서는 아래와 같다
    // <bean id=groupedOpenApi class=org.springdoc.core.GroupedOpenApi />
    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("lalala project") // 프로젝트 이름
                .pathsToMatch("/posts/**", "/users/**") // uri pattern
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("my api~~~") // API 명세서 제목
                                .description("내 API 명세서 입니다.") // API 명세서 내용
                                .version("v1.0.0") // API 명세서 버전
                );
    }






}
