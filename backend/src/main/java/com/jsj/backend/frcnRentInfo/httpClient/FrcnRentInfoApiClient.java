package com.jsj.backend.frcnRentInfo.httpClient;

import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoApiRequest;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * 농기계 임대 정보 API 클라이언트 클래스.
 * 이 클래스는 외부 API를 호출하여 농기계 임대 정보를 가져옵니다.
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FrcnRentInfoApiClient {

    private final RestClient restClient;

    /**
     * 외부 API를 호출하여 모든 농기계 임대 정보를 가져옵니다.
     *
     * @param request API 요청 객체
     * @return FrcnRentInfoApiResponse 농기계 임대 정보 응답 객체
     */
    public FrcnRentInfoApiResponse getAllOffice(FrcnRentInfoApiRequest request) {
        log.info("key: {}", request.getServiceKey());
        // URI 생성
        URI uri = URI.create(UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("api.data.go.kr")
                .path("/openapi/tn_pubr_public_frcn_rent_info_api")
                .queryParam("serviceKey", request.getServiceKey())
                .queryParam("pageNo", request.getPageNo())
                .queryParam("numOfRows", request.getNumOfRows())
                .queryParam("type", request.getType())
//                .queryParam("size", request.getSize())
//                .queryParam("page", request.getPage())
//                .queryParam("query", request.getQuery())
//                .queryParam("type", request.getType())
//                .queryParam("category", request.getCategory())
//                .queryParam("format", request.getFormat())
//                .queryParam("errorformat", request.getErrorFormat())
//                .queryParam("key", request.getKey())
                .build(false) // false로 설정하여 자동 인코딩을 비활성화
                .toUriString());

        log.info("Request URI: {}", uri);
        // REST API 호출 및 응답 반환
        return restClient.get()
                .uri(uri)
                .retrieve()
                .body(FrcnRentInfoApiResponse.class);
    }
}
