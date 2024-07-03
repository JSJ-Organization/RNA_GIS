package com.jsj.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * HTTP Client 를 위한 구성 클래스.
 * 이 클래스는 RestClient 빈을 정의하여 HTTP 통신에 사용됩니다.
 *
 * @Configuration 애노테이션을 사용하여 스프링 컨텍스트에서 구성 클래스로 인식됩니다.
 */
@Configuration
public class RestClientConfig {

    /**
     * RestClient 빈 정의.
     * 이 메서드는 API와 통신하기 위해 RestClient 객체를 생성합니다.
     *
     * @return 설정된 RestClient 객체
     */
    @Primary
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .build();
    }

    @Bean(name = "byteArrayRestClient")
    public RestClient xmlRestClient() {
        return RestClient.builder()
                .messageConverters(this::configureMessageConverters)
                .build();
    }

    private void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new ByteArrayHttpMessageConverter());
    }
}
