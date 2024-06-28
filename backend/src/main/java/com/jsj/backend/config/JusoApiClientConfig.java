package com.jsj.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class JusoApiClientConfig {
    @Value("${api.juso.key}")
    private String jusoApiBaseUrl;
    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(jusoApiBaseUrl)
                .build();
    }
}
