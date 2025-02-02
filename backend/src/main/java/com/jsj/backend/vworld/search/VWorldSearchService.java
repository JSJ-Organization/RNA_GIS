package com.jsj.backend.vworld.search;

import com.jsj.backend.exception.EmptyInputException;
import com.jsj.backend.vworld.log.VWorldLog;
import com.jsj.backend.vworld.log.VWorldLogRepository;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiRequest;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiResponse;
import com.jsj.backend.vworld.search.httpClient.VWorldSearchApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.jsj.backend.util.ValidationUtil.validateQuery;

/**
 * VWorld 검색 서비스를 제공하는 클래스.
 * 이 클래스는 VWorld 검색 API를 호출하고 응답을 처리하는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VWorldSearchService {

    private final VWorldLogRepository vWorldLogRepository; // VWorld 로그 저장소
    private final VWorldSearchApiClient vWorldSearchApiClient; // VWorld 검색 API 클라이언트

    @Value("${api.vworld.key}")
    private String API_KEY_VWORLD; // 외부 설정 파일에서 주입받는 VWorld API 키

    /**
     * VWorld 검색 API 응답을 로깅하고 로그 저장소에 저장합니다.
     *
     * @param query    검색한 쿼리
     * @param response VWorldSearchApiResponse 응답 객체
     */
    private void logVWorldSearchApiResponse(String query, VWorldSearchApiResponse response) {
        String errorCode = null;
        String errorText = null;
        Integer total = 0;
        if (response.getResponse() == null) {
            errorCode = "NOT_FOUND";
            errorText = "데이터가 존재하지 않습니다";
        } else if (response.getResponse().getError() != null) {
            errorCode = response.getResponse().getError().getCode();
            errorText = response.getResponse().getError().getText();
        } else {
            total = response.getResponse().getRecord().getTotal();
        }
        vWorldLogRepository.save(VWorldLog.builder()
                .searchQuery(query)
                .status(response.getResponse().getStatus())
                        .total(total)
                .errorCode(errorCode)
                .errorText(errorText)
                .build());
    }

    /**
     * 주어진 쿼리와 페이지 번호로 VWorld 검색 API를 호출하여 응답을 반환합니다.
     *
     * @param query 검색할 쿼리
     * @param page  페이지 번호
     * @return VWorldSearchApiResponse 응답 객체
     */
    public VWorldSearchApiResponse getPointWithPage(String query, Integer page) {
        log.info("query: {}, page: {}", query, page);
        validateQuery(query);

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
     * 주어진 쿼리로 VWorld 검색 API를 호출하여 응답을 반환합니다.
     *
     * @param query 검색할 쿼리
     * @return VWorldSearchApiResponse 응답 객체
     */
    public VWorldSearchApiResponse getPoint(String query) {
        log.info("query: {}", query);
        validateQuery(query);

        VWorldSearchApiResponse response = vWorldSearchApiClient.getPoint(
                VWorldSearchApiRequest.builder()
                        .key(API_KEY_VWORLD)
                        .query(query)
                        .build());

        logVWorldSearchApiResponse(query, response);
        return response;
    }
}
