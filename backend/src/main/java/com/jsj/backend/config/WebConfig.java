package com.jsj.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 관련 설정을 위한 구성 클래스.
 * 이 클래스는 애플리케이션의 CORS 설정을 구성합니다.
 *
 * @Configuration 애노테이션은 이 클래스가 @Bean 정의 메서드를 가지고 있음을 나타내며,
 * 스프링이 이 클래스를 처리하여 애플리케이션 컨텍스트에서 사용할 스프링 빈을 생성할 수 있게 합니다.
 */
@Configuration
public class WebConfig {

    @Value("${domain}")
    private String DOMAIN;

    /**
     * WebMvcConfigurer에 대한 빈 정의.
     * 이 빈은 애플리케이션의 CORS(Cross-Origin Resource Sharing) 설정을 사용자 정의합니다.
     *
     * @return CORS 설정을 구성하는 WebMvcConfigurer
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            /**
             * CORS 매핑을 구성합니다.
             *
             * 이 메서드는 지정된 출처(http://localhost:3000)에서 애플리케이션의 리소스에 대한 모든 HTTP 메서드
             * (GET, POST, PUT, DELETE 등)를 허용합니다.
             *
             * @param registry CORS 매핑을 추가할 CorsRegistry
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://localhost:5173", "http://localhost:80", "http://localhost:8080", "http://localhost", "https://" + DOMAIN)
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
