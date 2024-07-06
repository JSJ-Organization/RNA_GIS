package com.jsj.backend.frcnRentInfo;

import com.jsj.backend.batch.frcnRentInfo.FrcnRentInfoBatchService;
import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfoRepository;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLog;
import com.jsj.backend.frcnRentInfo.log.FrcnRentInfoLogRepository;
import com.jsj.backend.util.DateUtil;
import com.sun.jdi.InvalidTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jsj.backend.util.ValidationUtil.validateQuery;

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
    private final FrcnRentInfoBatchService frcnRentInfoBatchService;

    /**
     * 최신 배치 날짜로 농기계 임대 정보를 검색합니다.
     *
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByBatchDate() {
        String date = getValidBatchDate();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDate(date);
        logFrcnRentInfoResponse(null, new HashMap<>(), response);
        return response;
    }

    /**
     * 최신 배치 날짜와 주어진 필드 값이 포함된 농기계 임대 정보를 검색합니다.
     *
     * @param field 검색할 필드
     * @param value 검색할 값
     * @return FrcnRentInfo 엔티티 리스트
     */
    private List<FrcnRentInfo> findByBatchDateAndFieldContaining(String field, String value) {
        validateQuery(value);
        String date = getValidBatchDate();
        List<FrcnRentInfo> response;
        switch (field) {
            case "rdnmadr":
                response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContaining(date, value);
                break;
            case "lnmadr":
                response = frcnRentInfoRepository.findAllByBatchDateAndLnmadrContaining(date, value);
                break;
            case "officeNm":
                response = frcnRentInfoRepository.findAllByBatchDateAndOfficeNmContaining(date, value);
                break;
            case "institutionNm":
                response = frcnRentInfoRepository.findAllByBatchDateAndInstitutionNmContaining(date, value);
                break;
            case "keyword":
                response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContainingOrLnmadrContaining(date, value, value);
                break;
            default:
                throw new IllegalArgumentException("Invalid search field: " + field);
        }
        logFrcnRentInfoResponse(value, new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        return findByBatchDateAndFieldContaining("rdnmadr", rdnmadr);
    }

    public List<FrcnRentInfo> findByBatchDateAndLnmadrContaining(String lnmadr) {
        return findByBatchDateAndFieldContaining("lnmadr", lnmadr);
    }

    public List<FrcnRentInfo> findByBatchDateAndOfficeNmContaining(String officeNm) {
        return findByBatchDateAndFieldContaining("officeNm", officeNm);
    }

    public List<FrcnRentInfo> findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        return findByBatchDateAndFieldContaining("institutionNm", institutionNm);
    }

    public List<FrcnRentInfo> findByBatchDateAndKeywordContaining(String keyword) {
        return findByBatchDateAndFieldContaining("keyword", keyword);
    }

    /**
     * 주어진 좌표와 거리 내에 있는 농기계 임대 정보를 검색합니다.
     *
     * @param x        좌표의 경도
     * @param y        좌표의 위도
     * @param distance 검색할 거리
     * @return FrcnRentInfo 엔티티 리스트
     */
    public List<FrcnRentInfo> findByLocationWithin(double x, double y, Integer distance) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            validateQuery(x, y, distance);
        } catch (InvalidTypeException e) {
            errorMap.put("errorCode", "INVALID_TYPE");
            errorMap.put("errorText", e.getMessage());
        }
        String date = getValidBatchDate();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByLocationWithin(date, x, y, distance);
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

    /**
     * 주어진 날짜에 대한 데이터가 존재하는지 확인합니다.
     *
     * @param date 확인할 날짜 (yyyyMMdd 형식)
     * @return 데이터 존재 여부
     */
    public boolean existedDataWithDate(String date) {
        return frcnRentInfoRepository.existsByBatchDate(date);
    }

    /**
     * 유효한 배치 날짜를 가져옵니다. 오늘 날짜에 데이터가 없으면 최신 날짜를 가져옵니다.
     *
     * @return 유효한 배치 날짜 (yyyyMMdd 형식)
     */
    private String getValidBatchDate() {
        String date = DateUtil.getTodayDateString();
        if (!existedDataWithDate(date)) {
            date = frcnRentInfoRepository.findLatestBatchDate();
            upToData();
        }
        return date;
    }

    /**
     * 최신 데이터를 업데이트합니다.
     */
    public void upToData() {
        frcnRentInfoBatchService.frcnRentInfoJob();
    }
}
