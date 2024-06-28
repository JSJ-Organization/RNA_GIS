package com.jsj.backend.search;

import com.jsj.backend.search.juso.JusoApiClient;
import com.jsj.backend.search.juso.JusoApiRequest;
import com.jsj.backend.search.juso.JusoApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 검색 서비스를 제공하는 클래스.
 * 이 클래스는 Juso API를 통해 주소를 검색하는 기능을 제공합니다.
 *
 * @Service 애노테이션을 사용하여 스프링 컨텍스트에서 서비스 빈으로 관리됩니다.
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @Slf4j는 로깅을 위한 로거를 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final JusoApiClient jusoApiClient;

    private Integer DEFAULT_CURRENT_PAGE = 1; // 기본 현재 페이지
    private Integer COUNT_PER_PAGE = 5; // 페이지 당 결과 수
    @Value("${api.juso.key}")
    private String confmKey; // 외부 설정 파일에서 주입받는 Juso API 키
    private final String RESULT_TYPE = "json"; // 결과 형식

    /**
     * 주어진 키워드로 주소를 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return JusoApiResponse 주소 검색 결과를 담고 있는 응답 객체
     */
    public JusoApiResponse getAddress(String keyword) {

        log.info("keyword: {}", keyword);

        // Juso API 요청을 생성하고 호출
        return jusoApiClient.getAddress(
                JusoApiRequest.builder()
                        .currentPage(DEFAULT_CURRENT_PAGE)
                        .countPerPage(COUNT_PER_PAGE)
                        .keyword(keyword)
                        .confmKey(confmKey)
                        .resultType(RESULT_TYPE)
                        .build()
        );
    }

    /**
     * 주어진 키워드와 페이지 번호로 주소를 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @param currentPage 검색할 현재 페이지 번호
     * @return JusoApiResponse 주소 검색 결과를 담고 있는 응답 객체
     */
    public JusoApiResponse getAddressWithCurrentPage(String keyword, Integer currentPage) {

        log.info("keyword: {} , current page: {}", keyword, currentPage);

        // Juso API 요청을 생성하고 호출
        return jusoApiClient.getAddress(
                JusoApiRequest.builder()
                        .currentPage(currentPage)
                        .countPerPage(COUNT_PER_PAGE)
                        .keyword(keyword)
                        .confmKey(confmKey)
                        .resultType(RESULT_TYPE)
                        .build()
        );
    }
}
