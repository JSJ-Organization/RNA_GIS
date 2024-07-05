package com.jsj.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("주소지 검색 및 전국 농기계 임대 정보 검색 서비스 API")
                        .version("1.0")
                        .description("이 문서는 주소지 검색 및 농기계 임대 정보 검색 API에 대한 명세서입니다. "
                                + "API를 통해 다양한 주소지 검색 기능과 전국의 농기계 임대 정보를 조회할 수 있습니다. "
                                + "각 엔드포인트의 요청과 응답 형식, 예제 및 설명을 제공합니다."));
    }
}
