package com.jsj.backend.search;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 검색 요청을 처리하는 REST 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터의 검색 요청을 받아서 처리합니다.
 *
 * @RestController 애노테이션을 사용하여 스프링이 이 클래스를 RESTful 웹 서비스로 인식합니다.
 * @RequiredArgsConstructor는 final 필드에 대한 생성자를 자동으로 생성합니다.
 * @RequestMapping 애노테이션은 기본 URL 경로를 지정합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService service;

    /**
     * 주어진 키워드로 주소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param keyword 검색할 키워드
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/address")
    public ResponseEntity<?> getAddress(@RequestParam(name = "keyword") String keyword) {
        // SearchService를 사용하여 주소 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getAddress(keyword));
    }

    /**
     * 주어진 키워드와 페이지 번호로 주소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param keyword 검색할 키워드
     * @param currentPage 검색할 현재 페이지 번호
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/address-with-page")
    public ResponseEntity<?> getAddressWithPage(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "currentPage") Integer currentPage
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 주소 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getAddressWithCurrentPage(keyword, currentPage));
    }

    /**
     * 주어진 쿼리로 포인트를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param query 검색할 쿼리
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @GetMapping("/point")
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
    @GetMapping("/point-with-page")
    public ResponseEntity<?> getPointWithPage(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "page") Integer page
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getPointWithPage(query, page));
    }

    @GetMapping("/office-all")
    public ResponseEntity<?> getAllOffice(
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getAllOffice());
    }
}
