package com.jsj.backend.search.frcnRentInfo;

import com.jsj.backend.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 농기계 임대 정보 서비스 클래스.
 * 이 클래스는 농기계 임대 정보를 가져오는 기능을 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FrcnRentInfoService {
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
        // API 클라이언트를 사용하여 외부 API에서 데이터를 가져오고 매퍼를 사용하여 엔티티로 변환하여 반환합니다.
        return frcnRentInfoMapper.apiToEntity(frcnRentInfoApiClient.getAllOffice(FrcnRentInfoApiRequest.builder()
                .serviceKey(API_KEY_FRCN_RENT_INFO)
                .build()));
    }
    // 전국농기계임대정보 확인 로직
    // 1. 전체 주소
    public List<FrcnRentInfo> findByBatchDate() {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDate(todayDateString);
    }

    // 1.1. 정확한 도로명 주소
    public List<FrcnRentInfo> findByBatchDateAndRdnmadr(String rdnmadr) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndRdnmadr(todayDateString, rdnmadr);
    }
    // 1.1.1 like 도로명 주소
    public List<FrcnRentInfo> findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndRdnmadrContaining(todayDateString, rdnmadr);
    }

    // 1.2. 정확한 지번 주소
    public List<FrcnRentInfo> findByBatchDateAndLnmadr(String lnmadr) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndLnmadr(todayDateString, lnmadr);
    }
    // 1.2.1 like 지번 주소
    public List<FrcnRentInfo> findByBatchDateAndLnmadrContaining(String lnmadr) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndLnmadrContaining(todayDateString, lnmadr);
    }

    // 2. 사업지 명
    public List<FrcnRentInfo> findByBatchDateAndOfficeNm(String officeNm) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndOfficeNm(todayDateString, officeNm);
    }
    public List<FrcnRentInfo> findByBatchDateAndOfficeNmContaining(String officeNm) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndOfficeNmContaining(todayDateString, officeNm);
    }
    // 3. 관리기관명(청사)
    public List<FrcnRentInfo> findByBatchDateAndInstitutionNm(String institutionNm) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndInstitutionNm(todayDateString, institutionNm);
    }

    public List<FrcnRentInfo> findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByBatchDateAndInstitutionNmContaining(todayDateString, institutionNm);
    }

    // 4. 특정 장소의 좌표를 기반으로 주변(미터단위) 데이터를 가져오기
    public List<FrcnRentInfo> findByLocationWithin(double x, double y, Integer distance) {
        String todayDateString = DateUtil.getTodayDateString();
        return frcnRentInfoRepository.findAllByLocationWithin(todayDateString, x, y, distance);
    }
}
