package com.jsj.backend.frcnRentInfo;

import com.jsj.backend.exception.EmptyInputException;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoApiRequest;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoMapper;
import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfoRepository;
import com.jsj.backend.frcnRentInfo.httpClient.FrcnRentInfoApiClient;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLog;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLogRepository;
import com.jsj.backend.util.DateUtil;
import com.sun.jdi.InvalidTypeException;
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
public class FrcnRentInfoService {

    private final FrcnRentInfoLogRepository frcnRentInfoLogRepository; // 농기계 임대 정보 로그 저장소
    private final FrcnRentInfoRepository frcnRentInfoRepository; // 농기계 임대 정보 저장소
    private final FrcnRentInfoMapper frcnRentInfoMapper; // 농기계 임대 정보 매퍼 객체
    @Value("${api.frcn-rent-info.key}")
    private String API_KEY_FRCN_RENT_INFO; // 외부 설정 파일에서 주입받는 API 키
    private final FrcnRentInfoApiClient frcnRentInfoApiClient; // 농기계 임대 정보 API 클라이언트

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
        logFrcnRentInfoResponse("getAllApiInfo", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜로 농기계 임대 정보를 검색합니다.
     *
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDate() {
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDate(todayDateString);
        logFrcnRentInfoResponse("findByBatchDate", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 도로명 주소를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param rdnmadr 도로명 주소
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        checkEmpty(rdnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContaining(todayDateString, rdnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndRdnmadrContaining", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 지번 주소를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param lnmadr 지번 주소
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndLnmadr(String lnmadr) {
        checkEmpty(lnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndLnmadr(todayDateString, lnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndLnmadr", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 지번 주소를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param lnmadr 지번 주소
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndLnmadrContaining(String lnmadr) {
        checkEmpty(lnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndLnmadrContaining(todayDateString, lnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndLnmadrContaining", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 주소 키워드를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param keyword 주소 키워드
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndKeywordContaining(String keyword) {
        checkEmpty(keyword);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContainingOrLnmadrContaining(todayDateString, keyword, keyword);
        logFrcnRentInfoResponse("findByBatchDateAndKeywordContaining", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 사업소명을 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param officeNm 사업소명
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndOfficeNmContaining(String officeNm) {
        checkEmpty(officeNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndOfficeNmContaining(todayDateString, officeNm);
        logFrcnRentInfoResponse("findByBatchDateAndOfficeNmContaining", new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 관리기관명을 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param institutionNm 관리기관명
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        checkEmpty(institutionNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndInstitutionNmContaining(todayDateString, institutionNm);
        logFrcnRentInfoResponse("findByBatchDateAndInstitutionNmContaining", new HashMap<>(), response);
        return response;
    }

    /**
     * 주어진 좌표와 거리 내에 있는 농기계 임대 정보를 검색합니다.
     *
     * @param x 좌표의 경도
     * @param y 좌표의 위도
     * @param distance 검색할 거리
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByLocationWithin(double x, double y, Integer distance) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            checkEmpty(x, y, distance);
        } catch (InvalidTypeException e) {
            errorMap.put("errorCode", "INVALID_TYPE");
            errorMap.put("errorText", e.getMessage());
        }
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByLocationWithin(todayDateString, x, y, distance);
        logFrcnRentInfoResponse(String.format("x:%f y:%f distance:%d", x, y, distance), errorMap, response);
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
        String status = response.isEmpty() ? "NOT_FOUND" : error.isEmpty() ? "OK" : "ERROR";
        String errorCode = error.get("errorCode");
        String errorText = error.get("errorText");

        frcnRentInfoLogRepository.save(FrcnRentInfoLog.builder()
                .searchQuery(query)
                .status(status)
                .errorCode(errorCode)
                .errorText(errorText)
                .build());
    }

    /**
     * 입력 쿼리가 빈 값인지 확인하고 예외를 던집니다.
     *
     * @param query 확인할 쿼리
     */
    public static void checkEmpty(String query) {
        if (query == null || query.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new EmptyInputException(errorMessage);
        }
    }

    /**
     * 주어진 좌표와 거리가 유효한 값인지 확인하고 예외를 던집니다.
     *
     * @param x       경도
     * @param y       위도
     * @param distance 거리
     * @throws InvalidTypeException 유효하지 않은 값일 경우 던지는 예외
     */
    public static void checkEmpty(double x, double y, Integer distance) throws InvalidTypeException {
        if (distance == null) {
            String errorMessage = "distance 가 빈 값입니다:: distance: null";
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        } else if (distance <= 0) {
            String errorMessage = String.format("distance 가 0보다 큰 값이어야 합니다:: distance: %d", distance);
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        } else if (Double.isNaN(x) || Double.isNaN(y)) {
            String errorMessage = String.format("x, y 타입이 잘못되었습니다:: x: %f , y: %f", x, y);
            log.error(errorMessage);
            throw new InvalidTypeException(errorMessage);
        }
    }
}
