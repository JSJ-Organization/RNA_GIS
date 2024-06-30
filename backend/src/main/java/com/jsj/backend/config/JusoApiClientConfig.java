package com.jsj.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Juso API 클라이언트를 위한 구성 클래스.
 * 이 클래스는 RestClient 빈을 정의하여 Juso API와 통신하는 데 사용됩니다.
 *
 * @Configuration 애노테이션을 사용하여 스프링 컨텍스트에서 구성 클래스로 인식됩니다.
 */
@Configuration
public class JusoApiClientConfig {

    /**
     * RestClient 빈 정의.
     * 이 메서드는 Juso API와 통신하기 위해 RestClient 객체를 생성합니다.
     *
     * @return 설정된 RestClient 객체
     */
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .build();
    }
}
