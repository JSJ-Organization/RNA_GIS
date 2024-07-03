package com.jsj.backend.search;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 검색 요청을 처리하는 REST 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터의 검색 요청을 받아서 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService service;

    /**
     * 주어진 쿼리로 포인트를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param query 검색할 쿼리
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/api-point")
    public ResponseEntity<?> getPoint(@RequestParam(name = "query") String query) {
        // SearchService를 사용하여 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getPoint(query));
    }

    /**
     * 주어진 쿼리와 페이지 번호로 포인트를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param query 검색할 쿼리
     * @param page 검색할 현재 페이지 번호
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/api-point-with-page")
    public ResponseEntity<?> getPointWithPage(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page") Integer page
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getPointWithPage(query, page));
    }

    @GetMapping("/api-office-all")
    public ResponseEntity<?> getAllOffice(
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getAllOffice());
    }

    @GetMapping("/office")
    public ResponseEntity<?> searchOfficeWithXY(
            @RequestParam(name = "x") double x,
            @RequestParam(name = "y") double y,
            @RequestParam(name = "distance") Integer distance
    ) {
        return ResponseEntity.ok(service.findByLocationWithin(x, y, distance));
    }

    @GetMapping("/office-all")
    public ResponseEntity<?> searchAllOffice(
    ) {
        return ResponseEntity.ok(service.findByBatchDate());
    }

    @GetMapping("/office-fit-rdnmadr")
    public ResponseEntity<?> searchFitOfficeWithRdnmadr(
            @RequestParam(name = "rdnmadr") String rdnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndRdnmadr(rdnmadr));
    }

    @GetMapping("/office-rdnmadr")
    public ResponseEntity<?> searchOfficeWithRdnmadr(
            @RequestParam(name = "rdnmadr") String rdnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndRdnmadrContaining(rdnmadr));
    }

    @GetMapping("/office-fit-lnmadr")
    public ResponseEntity<?> searchFitOfficeWithLnmadr(
            @RequestParam(name = "lnmadr") String lnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndLnmadr(lnmadr));
    }

    @GetMapping("/office-lnmadr")
    public ResponseEntity<?> searchOfficeWithLnmadr(
            @RequestParam(name = "lnmadr") String lnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndLnmadrContaining(lnmadr));
    }

    @GetMapping("/office-fit-name")
    public ResponseEntity<?> searchOfficeWithOfficeName(
            @RequestParam(name = "officeNm") String officeNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndOfficeNm(officeNm));
    }

    @GetMapping("/office-name")
    public ResponseEntity<?> searchOfficeWithOfficeNameContaining(
            @RequestParam(name = "officeNm") String officeNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndOfficeNmContaining(officeNm));
    }

    @GetMapping("/office-fit-institution")
    public ResponseEntity<?> searchOfficeWithInstitutionName(
            @RequestParam(name = "institutionNm") String institutionNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndInstitutionNm(institutionNm));
    }

    @GetMapping("/office-institution")
    public ResponseEntity<?> searchOfficeWithInstitutionNameContaining(
            @RequestParam(name = "institutionNm") String institutionNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndInstitutionNmContaining(institutionNm));
    }
}
