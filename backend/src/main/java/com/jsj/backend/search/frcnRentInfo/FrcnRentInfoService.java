package com.jsj.backend.search.frcnRentInfo;

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
    public List<FrcnRentInfo> getAllInfo() {
        // API 클라이언트를 사용하여 외부 API에서 데이터를 가져오고 매퍼를 사용하여 엔티티로 변환하여 반환합니다.
        return frcnRentInfoMapper.apiToEntity(frcnRentInfoApiClient.getAllOffice(FrcnRentInfoApiRequest.builder()
                .serviceKey(API_KEY_FRCN_RENT_INFO)
                .build()));
    }

}
