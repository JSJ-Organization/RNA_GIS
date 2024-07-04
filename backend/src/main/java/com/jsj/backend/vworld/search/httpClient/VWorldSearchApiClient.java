package com.jsj.backend.vworld.search.httpClient;

import com.jsj.backend.vworld.search.dto.VWorldSearchApiRequest;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * VWorld 검색 API 클라이언트 클래스.
 * 이 클래스는 VWorld API를 호출하여 검색 요청을 처리합니다.
 *
 * @Component 애노테이션을 사용하여 스프링 컨텍스트에서 빈으로 관리됩니다.
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @Slf4j는 로깅을 위한 로거를 제공합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VWorldSearchApiClient {

    private final RestClient restClient;

    /**
     * VWorld API를 호출하여 포인트 검색 결과를 반환합니다.
     *
     * @param request 검색 요청 객체
     * @return VWorldSearchApiResponse 검색 응답 객체
     */
    public VWorldSearchApiResponse getPoint(VWorldSearchApiRequest request) {

        // URI 생성
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.vworld.kr")
                .path("/req/search")
                .queryParam("service", request.getService())
                .queryParam("request", request.getRequest())
                .queryParam("version", request.getVersion())
                .queryParam("crs", request.getCrs())
                .queryParam("size", request.getSize())
                .queryParam("page", request.getPage())
                .queryParam("query", request.getQuery())
                .queryParam("type", request.getType())
                .queryParam("category", request.getCategory())
                .queryParam("format", request.getFormat())
                .queryParam("errorformat", request.getErrorFormat())
                .queryParam("key", request.getKey())
                .build()
                .toUri();

        log.info("Request URI: {}", uri);

        // REST API 호출 및 응답 반환
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(VWorldSearchApiResponse.class);
    }
}
