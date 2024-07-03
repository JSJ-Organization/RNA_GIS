package com.jsj.backend.vworld.wmts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

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
public class VWorldWMTSApiClient {

    @Qualifier("byteArrayRestClient")
    private final RestClient xmlRestClient;

    /**
     * VWorld API를 호출하여 포인트 검색 결과를 반환합니다.
     *
     * @param request 검색 요청 객체
     * @return VWorldSearchApiResponse 검색 응답 객체
     */
    public byte[] getTile(VWorldWMTSApiRequest request) {

        // URI 생성
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("api.vworld.kr")
                .path("/req/wmts/1.0.0/{key}/Satellite/{tileMatrix}/{tileRow}/{tileCol}.{tileType}")
                .buildAndExpand(request.getKey(), request.getTileMatrix(), request.getTileRow(), request.getTileCol(), request.getTileType())
                .toUri();

        log.info("Request URI: {}", uri);

        // REST API 호출 및 응답 반환
        return xmlRestClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_XML)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .body(byte[].class);
    }
}