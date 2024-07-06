package com.jsj.backend.search;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.FrcnRentInfoService;
import com.jsj.backend.frcnRentInfo.FrcnRentInfoApiService;
import com.jsj.backend.search.dto.SearchMapper;
import com.jsj.backend.search.dto.SearchResponse;
import com.jsj.backend.vworld.search.dto.VWorldSearchApiResponse;
import com.jsj.backend.vworld.search.VWorldSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 검색 서비스를 제공하는 클래스.
 * 이 클래스는 API를 통해 주소를 검색하는 기능을 제공합니다.
 *
 * @Service 애노테이션을 사용하여 스프링 컨텍스트에서 서비스 빈으로 관리됩니다.
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @Slf4j는 로깅을 위한 로거를 제공합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final VWorldSearchService vWorldSearchService; // VWorld 검색 서비스 객체
    private final FrcnRentInfoService frcnRentInfoService; // 농기계 임대 정보 서비스 객체
    private final FrcnRentInfoApiService frcnRentInfoApiService; // 농기계 임대 정보 api 서비스 객체
    private final SearchMapper mapper; // 검색 매퍼 객체

    /**
     * 주어진 쿼리로 포인트를 검색합니다.
     *
     * @param query 검색할 쿼리
     * @return SearchResponse 포인트 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse getPoint(String query) {
        VWorldSearchApiResponse apiResponse = vWorldSearchService.getPoint(query);
        return mapper.vWorldSearchToSearch(query, apiResponse);
    }

    /**
     * 주어진 쿼리와 페이지 번호로 포인트를 검색합니다.
     *
     * @param query 검색할 쿼리
     * @param page  검색할 현재 페이지 번호
     * @return SearchResponse 포인트 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse getPointWithPage(String query, Integer page) {
        VWorldSearchApiResponse apiResponse = vWorldSearchService.getPointWithPage(query, page);
        return mapper.vWorldSearchToSearch(query, apiResponse);
    }

    /**
     * 모든 농기계 임대 정보를 검색합니다.
     *
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse getAllOffice() {
        List<FrcnRentInfo> entities = frcnRentInfoApiService.getAllApiInfo();
        return mapper.frcnRentInfoToSearch(entities, "", 1);
    }

    /**
     * 최신 배치 날짜로 농기계 임대 정보를 검색합니다.
     *
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDate() {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDate();
        return mapper.frcnRentInfoToSearch(entities, "", 1);
    }

    /**
     * 최신 배치 날짜와 주어진 도로명 주소를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param rdnmadr 도로명 주소
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndRdnmadrContaining(rdnmadr);
        return mapper.frcnRentInfoToSearch(entities, rdnmadr, 1);
    }

    /**
     * 최신 배치 날짜와 주어진 지번 주소를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param lnmadr 지번 주소
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDateAndLnmadrContaining(String lnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndLnmadrContaining(lnmadr);
        return mapper.frcnRentInfoToSearch(entities, lnmadr, 1);
    }

    /**
     * 최신 배치 날짜와 주어진 주소 키워드를 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param keyword 주소 키워드
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDateAndKeywordContaining(String keyword) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndKeywordContaining(keyword);
        return mapper.frcnRentInfoToSearch(entities, keyword, 1);
    }

    /**
     * 최신 배치 날짜와 주어진 사업소명을 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param officeNm 사업소명
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDateAndOfficeNmContaining(String officeNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndOfficeNmContaining(officeNm);
        return mapper.frcnRentInfoToSearch(entities, officeNm, 1);
    }

    /**
     * 최신 배치 날짜와 주어진 관리기관명을 포함하는 농기계 임대 정보를 검색합니다.
     *
     * @param institutionNm 관리기관명
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndInstitutionNmContaining(institutionNm);
        return mapper.frcnRentInfoToSearch(entities, institutionNm, 1);
    }

    /**
     * 주어진 좌표와 거리 내에 있는 농기계 임대 정보를 검색합니다.
     *
     * @param x 좌표의 경도
     * @param y 좌표의 위도
     * @param distance 검색할 거리
     * @return SearchResponse 농기계 임대 정보 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse findByLocationWithin(double x, double y, Integer distance) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByLocationWithin(x, y, distance);
        return mapper.frcnRentInfoToSearch(entities, String.format("x:%f y:%f distance: %d", x, y, distance), 1);
    }
}
