package com.jsj.backend.search.frcnRentInfo;

import com.jsj.backend.exception.EmptyInputException;
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
    private final FrcnRentInfoLogRepository frcnRentInfoLogRepository;
    private final FrcnRentInfoRepository frcnRentInfoRepository;
    private final FrcnRentInfoMapper frcnRentInfoMapper;
    @Value("${api.frcn-rent-info.key}")
    private String API_KEY_FRCN_RENT_INFO;
    private final FrcnRentInfoApiClient frcnRentInfoApiClient;

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

    public List<FrcnRentInfo> findByBatchDate() {
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDate(todayDateString);
        logFrcnRentInfoResponse("findByBatchDate", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndRdnmadr(String rdnmadr) {
        checkEmpty(rdnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadr(todayDateString, rdnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndRdnmadr", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        checkEmpty(rdnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContaining(todayDateString, rdnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndRdnmadrContaining", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndLnmadr(String lnmadr) {
        checkEmpty(lnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndLnmadr(todayDateString, lnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndLnmadr", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndLnmadrContaining(String lnmadr) {
        checkEmpty(lnmadr);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndLnmadrContaining(todayDateString, lnmadr);
        logFrcnRentInfoResponse("findByBatchDateAndLnmadrContaining", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndOfficeNm(String officeNm) {
        checkEmpty(officeNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndOfficeNm(todayDateString, officeNm);
        logFrcnRentInfoResponse("findByBatchDateAndOfficeNm", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndOfficeNmContaining(String officeNm) {
        checkEmpty(officeNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndOfficeNmContaining(todayDateString, officeNm);
        logFrcnRentInfoResponse("findByBatchDateAndOfficeNmContaining", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndInstitutionNm(String institutionNm) {
        checkEmpty(institutionNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndInstitutionNm(todayDateString, institutionNm);
        logFrcnRentInfoResponse("findByBatchDateAndInstitutionNm", new HashMap<>(), response);
        return response;
    }

    public List<FrcnRentInfo> findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        checkEmpty(institutionNm);
        String todayDateString = DateUtil.getTodayDateString();
        List<FrcnRentInfo> response = frcnRentInfoRepository.findAllByBatchDateAndInstitutionNmContaining(todayDateString, institutionNm);
        logFrcnRentInfoResponse("findByBatchDateAndInstitutionNmContaining", new HashMap<>(), response);
        return response;
    }

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

    public static void checkEmpty(String query) {
        if (query == null || query.trim().isEmpty()) {
            String errorMessage = "검색이 불가능합니다:: 키워드가 빈 값입니다.";
            log.error(errorMessage);
            throw new EmptyInputException(errorMessage);
        }
    }

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
