package com.jsj.backend.search.juso;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Juso API 클라이언트 클래스.
 * 이 클래스는 주소 검색을 위해 Juso API를 호출하는 메서드를 제공합니다.
 *
 * @Component 애노테이션을 사용하여 스프링 컨텍스트에서 빈으로 관리됩니다.
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @Slf4j는 로깅을 위한 로거를 제공합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JusoApiClient {
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    // Juso API의 기본 URL
    @Value("${api.juso.base-url}")
    private String baseUrl;

    /**
     * 주어진 요청을 기반으로 주소 정보를 조회합니다.
     *
     * @param request 주소 검색을 위한 요청 객체
     * @return JusoApiResponse 주소 검색 결과를 담고 있는 응답 객체
     * @throws RuntimeException JSON 처리 중 예외가 발생하면 던집니다.
     */
    public JusoApiResponse getAddress(JusoApiRequest request) {
        // 요청 파라미터를 포함한 URL 경로 변수 생성
        String pathVariable = String.format("?currentPage=%s&countPerPage=%s&keyword=%s&confmKey=%s&resultType=%s",
                request.getCurrentPage(),
                request.getCountPerPage(),
                request.getKeyword(),
                request.getConfmKey(),
                request.getResultType());

        log.info(String.format("request api: %s", baseUrl+pathVariable));

        try {
            // REST API 호출 후 응답을 객체로 변환하여 반환
            return objectMapper.readValue(
                    restClient.get()
                            .uri(baseUrl + pathVariable)
                            .retrieve()
                            .body(String.class), JusoApiResponse.class); // String -> JusoApiResponse
        } catch (JsonProcessingException e) {
            // JSON 처리 예외 발생 시 런타임 예외로 던짐
            throw new RuntimeException(e);
        }
    }
}