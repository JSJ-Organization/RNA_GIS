package com.jsj.backend.frcnRentInfo;

import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoApiRequest;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoMapper;
import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.httpClient.FrcnRentInfoApiClient;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLog;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 농기계 임대 정보 서비스 클래스.
 * 이 클래스는 농기계 임대 정보를 가져오는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FrcnRentInfoApiService {

    private final FrcnRentInfoLogRepository frcnRentInfoLogRepository; // 농기계 임대 정보 로그 저장소
    private final FrcnRentInfoMapper frcnRentInfoMapper; // 농기계 임대 정보 매퍼 객체
    private final FrcnRentInfoApiClient frcnRentInfoApiClient; // 농기계 임대 정보 API 클라이언트
    @Value("${api.frcn-rent-info.key}")
    private String API_KEY_FRCN_RENT_INFO; // 외부 설정 파일에서 주입받는 API 키

    /**
     * 모든 농기계 임대 정보를 가져옵니다.
     *
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> getAllApiInfo() {
        List<FrcnRentInfo> response = frcnRentInfoMapper.apiToEntity(
                frcnRentInfoApiClient.getAllOffice(FrcnRentInfoApiRequest.builder()
                        .serviceKey(API_KEY_FRCN_RENT_INFO)
                        .build())
        );
        logFrcnRentInfoResponse(null, new HashMap<>(), response);
        return response;
    }

    /**
     * 농기계 임대 정보 응답을 로깅합니다.
     *
     * @param query    검색 쿼리
     * @param error    에러 정보
     * @param response 응답 객체
     */
    private void logFrcnRentInfoResponse(String query, Map<String, String> error, List<FrcnRentInfo> response) {
        String status;
        String errorCode = error.get("errorCode");
        String errorText = error.get("errorText");
        log.info("Error Code: {}, Error Text: {}", errorCode, errorText);
        Integer total = 0;
        if (response.isEmpty()) {
            status = "NOT_FOUND";
        } else if (error.isEmpty()) {
            status = "OK";
            total = response.size();
        } else {
            status = "ERROR";
        }

        frcnRentInfoLogRepository.save(FrcnRentInfoLog.builder()
                .searchQuery(query)
                .status(status)
                .total(total)
                .errorCode(errorCode)
                .errorText(errorText)
                .build());
    }
}
