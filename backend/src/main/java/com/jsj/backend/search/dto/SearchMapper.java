package com.jsj.backend.search.dto;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoMapper;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiResponse;
import com.jsj.backend.vworld.search.dto.VWorldSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 검색 결과를 매핑하는 클래스.
 * 이 클래스는 다양한 검색 API 응답을 공통의 SearchResponse 형식으로 변환합니다.
 *
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @Service 애노테이션을 사용하여 스프링 컨텍스트에서 서비스 빈으로 관리됩니다.
 */
@RequiredArgsConstructor
@Service
public class SearchMapper {

    private final FrcnRentInfoMapper frcnRentInfoMapper; // 농기계 임대 정보 매퍼 객체
    private final VWorldSearchMapper vWorldSearchMapper; // VWorld 검색 매퍼 객체

    private static final String FRCN_RENT_INFO_API_CRS = "EPSG:4326"; // 좌표계 정보

    /**
     * VWorld 검색 API 응답을 SearchResponse로 변환합니다.
     *
     * @param requestQuery 검색 요청 쿼리
     * @param apiResponse VWorld 검색 API 응답 객체
     * @return SearchResponse 변환된 검색 응답 객체
     */
    public SearchResponse vWorldSearchToSearch(
            String requestQuery,
            VWorldSearchApiResponse apiResponse
    ) {

        return SearchResponse.builder()
                .totalData(apiResponse.getResponse().getRecord().getTotal())
                .currentPage(apiResponse.getResponse().getPage().getCurrent())
                .totalPage(apiResponse.getResponse().getPage().getTotal())
                .requestQuery(requestQuery)
                .crs(FRCN_RENT_INFO_API_CRS)
                .vWorldSearchResponses(Optional.ofNullable(vWorldSearchMapper.apiToResponse(apiResponse)))
                .build();
    }

    /**
     * 농기계 임대 정보 엔티티 리스트를 SearchResponse로 변환합니다.
     *
     * @param entities 농기계 임대 정보 엔티티 리스트
     * @param requestQuery 검색 요청 쿼리
     * @param currentPage 현재 페이지 번호
     * @return SearchResponse 변환된 검색 응답 객체
     */
    public SearchResponse frcnRentInfoToSearch(
            List<FrcnRentInfo> entities,
            String requestQuery,
            Integer currentPage
    ) {

        return SearchResponse.builder()
                .currentPage(currentPage)
                .totalData(entities.size())
                .requestQuery(requestQuery)
                .crs(FRCN_RENT_INFO_API_CRS)
                .frcnRentInfoResponses(Optional.ofNullable(frcnRentInfoMapper.entityToResponse(entities)))
                .build();
    }
}
