package com.jsj.backend.search;

import com.jsj.backend.exception.EmptyInputException;
import com.jsj.backend.exception.InvalidInputException;
import com.jsj.backend.search.juso.JusoApiClient;
import com.jsj.backend.search.juso.JusoApiRequest;
import com.jsj.backend.search.juso.JusoApiResponse;
import com.jsj.backend.search.vworld.*;
import com.jsj.backend.util.CleanInputUtil;
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

    private final VWorldSearchApiClient vWorldSearchApiClient;

    @Value("${api.vworld.key}")
    private String API_KEY_VWORLD; // 외부 설정 파일에서 주입받는 VWorld API 키

    private final VWorldLogRepository vWorldLogRepository; // VWorld 로그 저장소

    /**
     * 주어진 키워드로 주소를 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return JusoApiResponse 주소 검색 결과를 담고 있는 응답 객체
     */
    public JusoApiResponse getAddress(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new EmptyInputException(errorMessage);
        }
        log.info("keyword: {}", keyword);
        String cleanKeyword = CleanInputUtil.cleanInput(keyword);
        if (cleanKeyword == null || cleanKeyword.trim().isEmpty()) {
            String errorMessage = String.format("잘못된 요청이 확인되었습니다. 요청값:: %s", keyword);
            log.error(errorMessage);
            throw new InvalidInputException(errorMessage);
        }
        // Juso API 요청을 생성하고 호출
        return jusoApiClient.getAddress(
                JusoApiRequest.builder()
                        .currentPage(DEFAULT_CURRENT_PAGE)
                        .countPerPage(COUNT_PER_PAGE)
                        .keyword(cleanKeyword)
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
        if (keyword == null || keyword.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new EmptyInputException(errorMessage);
        }
        log.info("keyword: {} , current page: {}", keyword, currentPage);
        String cleanKeyword = CleanInputUtil.cleanInput(keyword);
        if (cleanKeyword == null || cleanKeyword.trim().isEmpty()) {
            String errorMessage = String.format("잘못된 요청이 확인되었습니다. 요청값:: %s", keyword);
            log.error(errorMessage);
            throw new InvalidInputException(errorMessage);
        }
        // Juso API 요청을 생성하고 호출
        return jusoApiClient.getAddress(
                JusoApiRequest.builder()
                        .currentPage(currentPage)
                        .countPerPage(COUNT_PER_PAGE)
                        .keyword(cleanKeyword)
                        .confmKey(confmKey)
                        .resultType(RESULT_TYPE)
                        .build()
        );
    }

    /**
     * 주어진 쿼리로 포인트를 검색합니다.
     *
     * @param query 검색할 쿼리
     * @return VWorldSearchApiResponse 포인트 검색 결과를 담고 있는 응답 객체
     */
    public VWorldSearchApiResponse getPoint(String query) {
        log.info("query: {}", query);
        checkEmpty(query);

        VWorldSearchApiResponse response = vWorldSearchApiClient.getPoint(
                VWorldSearchApiRequest.builder()
                        .key(API_KEY_VWORLD)
                        .query(query)
                        .build());

        logVWorldSearchApiResponse(query, response);

        return response;
    }

    /**
     * 주어진 쿼리와 페이지 번호로 포인트를 검색합니다.
     *
     * @param query 검색할 쿼리
     * @param page 검색할 현재 페이지 번호
     * @return VWorldSearchApiResponse 포인트 검색 결과를 담고 있는 응답 객체
     */
    public VWorldSearchApiResponse getPointWithPage(String query, Integer page) {
        log.info("query: {}, page: {}", query, page);
        checkEmpty(query);

        VWorldSearchApiResponse response = vWorldSearchApiClient.getPoint(
                VWorldSearchApiRequest.builder()
                        .key(API_KEY_VWORLD)
                        .query(query)
                        .page(page)
                        .build());

        logVWorldSearchApiResponse(query, response);

        return response;
    }

    /**
     * 입력 쿼리가 빈 값인지 확인하고 예외를 던집니다.
     *
     * @param query 확인할 쿼리
     */
    private static void checkEmpty(String query) {
        if (query == null || query.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new EmptyInputException(errorMessage);
        }
    }

    /**
     * VWorld 검색 API 응답을 로깅하고 로그 저장소에 저장합니다.
     *
     * @param query 검색한 쿼리
     * @param response VWorldSearchApiResponse 응답 객체
     */
    private void logVWorldSearchApiResponse(String query, VWorldSearchApiResponse response) {
        String errorCode = null;
        String errorText = null;
        if (response.getResponse().getError() != null) {
            errorCode = response.getResponse().getError().getCode();
            errorText = response.getResponse().getError().getText();
        }
        vWorldLogRepository.save(VWorldLog.builder()
                .searchQuery(query)
                .status(response.getResponse().getStatus())
                .errorCode(errorCode)
                .errorText(errorText)
                .build());
    }
}
