package com.jsj.backend.vworld.search;

import com.jsj.backend.exception.EmptyInputException;
import com.jsj.backend.vworld.log.VWorldLog;
import com.jsj.backend.vworld.log.VWorldLogRepository;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiRequest;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiResponse;
import com.jsj.backend.vworld.search.dto.VWorldSearchResponse;
import com.jsj.backend.vworld.search.httpClient.VWorldSearchApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class VWorldSearchService {

    private final VWorldLogRepository vWorldLogRepository; // VWorld 로그 저장소
    private final VWorldSearchApiClient vWorldSearchApiClient;
    @Value("${api.vworld.key}")
    private String API_KEY_VWORLD; // 외부 설정 파일에서 주입받는 VWorld API 키

    public List<VWorldSearchResponse> apiToResponse(VWorldSearchApiResponse apiResponse) {
        List<VWorldSearchApiResponse.Response.Result.Item> items = apiResponse.getResponse().getResult().getItems();

        return items.stream().map(item -> VWorldSearchResponse.builder()
                .x(Double.parseDouble(item.getPoint().getX()))
                .y(Double.parseDouble(item.getPoint().getY()))
                .roadNameAddress(item.getAddress().getRoad())
                .parcelAddress(item.getAddress().getParcel())
                .zipcode(item.getAddress().getZipcode())
                .bldnm(item.getAddress().getBldnm())
                .bldnmdc(item.getAddress().getBldnmdc())
                .build()
        ).collect(toList());
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
     * @param query    검색한 쿼리
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
}
