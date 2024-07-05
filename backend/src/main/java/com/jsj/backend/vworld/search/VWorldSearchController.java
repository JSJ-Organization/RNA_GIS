package com.jsj.backend.vworld.search;

import com.jsj.backend.search.SearchService;
import com.jsj.backend.search.dto.SearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * VWorld 검색 요청을 처리하는 REST 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터의 검색 요청을 받아서 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@Schema(description = "VWorld 검색 요청을 처리하는 REST 컨트롤러 클래스")
@ApiResponse(content = @Content(schema = @Schema(implementation = SearchResponse.class)))
public class VWorldSearchController {

    private final SearchService service; // 검색 서비스 객체

    /**
     * 주어진 쿼리로 포인트를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param query 검색할 쿼리
     * @return 검색 결과를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "쿼리로 포인트 검색", description = "제공된 쿼리를 기반으로 포인트를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 쿼리 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/api-point")
    public ResponseEntity<?> getPoint(
            @Parameter(description = "검색어", example = "서울") @RequestParam(name = "query") String query
    ) {
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
    @Operation(summary = "페이지네이션된 쿼리로 포인트 검색", description = "제공된 쿼리와 페이지 번호를 기반으로 포인트를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 쿼리 또는 페이지 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/api-point-with-page")
    public ResponseEntity<?> getPointWithPage(
            @Parameter(description = "검색어", example = "서울") @RequestParam(name = "query") String query,
            @Parameter(description = "응답 페이지", example = "1") @RequestParam(name = "page") Integer page
    ) {
        // SearchService를 사용하여 페이지 번호를 포함한 포인트 검색 결과를 얻어와서 클라이언트에 반환
        return ResponseEntity.ok(service.getPointWithPage(query, page));
    }
}
