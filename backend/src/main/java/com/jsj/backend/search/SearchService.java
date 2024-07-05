package com.jsj.backend.search;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.FrcnRentInfoService;
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
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final VWorldSearchService vWorldSearchService;

    private final FrcnRentInfoService frcnRentInfoService;

    private final SearchMapper mapper;

    /**
     * 주어진 쿼리로 포인트를 검색합니다.
     *
     * @param query 검색할 쿼리
     * @return VWorldSearchApiResponse 포인트 검색 결과를 담고 있는 응답 객체
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
     * @return VWorldSearchApiResponse 포인트 검색 결과를 담고 있는 응답 객체
     */
    public SearchResponse getPointWithPage(String query, Integer page) {
        VWorldSearchApiResponse apiResponse = vWorldSearchService.getPointWithPage(query, page);
        return mapper.vWorldSearchToSearch(query, apiResponse);
    }

    public SearchResponse getAllOffice() {
        List<FrcnRentInfo> entities = frcnRentInfoService.getAllApiInfo();
        return mapper.frcnRentInfoToSearch(entities, "", 1);
    }

    public SearchResponse findByBatchDate() {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDate();
        return mapper.frcnRentInfoToSearch(entities, "", 1);
    }

    public SearchResponse findByBatchDateAndRdnmadr(String rdnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndRdnmadr(rdnmadr);
        return mapper.frcnRentInfoToSearch(entities, rdnmadr, 1);
    }

    public SearchResponse findByBatchDateAndRdnmadrContaining(String rdnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndRdnmadrContaining(rdnmadr);
        return mapper.frcnRentInfoToSearch(entities, rdnmadr, 1);
    }

    public SearchResponse findByBatchDateAndLnmadr(String lnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndLnmadr(lnmadr);
        return mapper.frcnRentInfoToSearch(entities, lnmadr, 1);
    }

    public SearchResponse findByBatchDateAndLnmadrContaining(String lnmadr) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndLnmadrContaining(lnmadr);
        return mapper.frcnRentInfoToSearch(entities, lnmadr, 1);
    }

    public SearchResponse findByBatchDateAndOfficeNm(String officeNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndOfficeNm(officeNm);
        return mapper.frcnRentInfoToSearch(entities, officeNm, 1);
    }

    public SearchResponse findByBatchDateAndOfficeNmContaining(String officeNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndOfficeNmContaining(officeNm);
        return mapper.frcnRentInfoToSearch(entities, officeNm, 1);
    }

    public SearchResponse findByBatchDateAndInstitutionNm(String institutionNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndInstitutionNm(institutionNm);
        return mapper.frcnRentInfoToSearch(entities, institutionNm, 1);
    }

    public SearchResponse findByBatchDateAndInstitutionNmContaining(String institutionNm) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByBatchDateAndInstitutionNmContaining(institutionNm);
        return mapper.frcnRentInfoToSearch(entities, institutionNm, 1);
    }

    public SearchResponse findByLocationWithin(double x, double y, Integer distance) {
        List<FrcnRentInfo> entities = frcnRentInfoService.findByLocationWithin(x, y, distance);
        return mapper.frcnRentInfoToSearch(entities, String.format("x:%f y:%f distance: %d", x, y, distance), 1);
    }
}
