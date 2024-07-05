package com.jsj.backend.frcnRentInfo;

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
 * 농기계 임대 정보 검색 요청을 처리하는 REST 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터의 검색 요청을 받아서 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@ApiResponse(content = @Content(schema = @Schema(implementation = SearchResponse.class)))
public class FrcnRentInfoController {

    private final SearchService service; // 검색 서비스 객체

    /**
     * 공공 API를 통해 모든 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @return 모든 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "공공 API로 모든 사업소 검색", description = "모든 사업소 레코드를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/api-office-all")
    public ResponseEntity<?> getAllOffice() {
        return ResponseEntity.ok(service.getAllOffice());
    }

    /**
     * 주어진 좌표와 거리를 기준으로 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param x 경도 (x 좌표)
     * @param y 위도 (y 좌표)
     * @param distance 검색 반경 거리
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "좌표로 사업소 검색", description = "제공된 좌표와 거리 기준으로 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 좌표 또는 거리 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office")
    public ResponseEntity<?> searchOfficeWithXY(
            @Parameter(description = "경도 (x 좌표): -180 <= x <= 180", example = "128.6552681") @RequestParam(name = "x") double x,
            @Parameter(description = "위도 (y 좌표): -90 <= y <= 90", example = "36.77318427") @RequestParam(name = "y") double y,
            @Parameter(description = "distance 좌표를 기준으로 반경 m", example = "100000") @RequestParam(name = "distance") Integer distance
    ) {
        return ResponseEntity.ok(service.findByLocationWithin(x, y, distance));
    }

    /**
     * 배치 날짜를 기준으로 모든 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @return 배치 날짜 기준 모든 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "모든 사업소 검색", description = "배치 날짜를 기준으로 모든 사업소 레코드를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-all")
    public ResponseEntity<?> searchAllOffice() {
        return ResponseEntity.ok(service.findByBatchDate());
    }

    /**
     * 주어진 도로명 주소를 포함하는 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param rdnmadr 도로명 주소
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "도로명 주소 포함하여 사업소 검색", description = "제공된 도로명 주소를 포함하여 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 도로명 주소 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-rdnmadr")
    public ResponseEntity<?> searchOfficeWithRdnmadr(
            @Parameter(description = "도로명 주소", example = "서울") @RequestParam(name = "rdnmadr") String rdnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndRdnmadrContaining(rdnmadr));
    }

    /**
     * 주어진 지번 주소를 포함하는 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param lnmadr 지번 주소
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "지번 주소 포함하여 사업소 검색", description = "제공된 지번 주소를 포함하여 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 지번 주소 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-lnmadr")
    public ResponseEntity<?> searchOfficeWithLnmadr(
            @Parameter(description = "지번 주소", example = "서울") @RequestParam(name = "lnmadr") String lnmadr
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndLnmadrContaining(lnmadr));
    }

    /**
     * 주어진 주소 키워드를 포함하는 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param keyword 주소 키워드
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "지번 주소 포함하여 사업소 검색", description = "제공된 지번 주소를 포함하여 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 지번 주소 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-address")
    public ResponseEntity<?> searchOfficeWithKeyword(
            @Parameter(description = "주소 키워드", example = "서울") @RequestParam(name = "keyword") String keyword
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndKeywordContaining(keyword));
    }

    /**
     * 주어진 사업소 이름을 포함하는 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param officeNm 사업소 이름
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "사업소 이름으로 검색", description = "제공된 사업소 이름을 기반으로 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 사업소 이름 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-name")
    public ResponseEntity<?> searchOfficeWithOfficeNameContaining(
            @Parameter(description = "사업소 이름", example = "사업소") @RequestParam(name = "officeNm") String officeNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndOfficeNmContaining(officeNm));
    }

    /**
     * 주어진 관리 기관 이름을 포함하는 사업소를 검색하는 HTTP GET 요청을 처리합니다.
     *
     * @param institutionNm 관리 기관 이름
     * @return 검색된 사업소 정보를 담고 있는 ResponseEntity 객체
     */
    @Operation(summary = "관리 기관 이름 포함하여 사업소 검색", description = "제공된 관리 기관 이름을 포함하여 사업소를 검색합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 처리됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 기관 이름 파라미터"),
            @ApiResponse(responseCode = "500", description = "내부 서버 오류")
    })
    @GetMapping("/api/v1/search/office-institution")
    public ResponseEntity<?> searchOfficeWithInstitutionNameContaining(
            @Parameter(description = "관리 기관 이름", example = "시청") @RequestParam(name = "institutionNm") String institutionNm
    ) {
        return ResponseEntity.ok(service.findByBatchDateAndInstitutionNmContaining(institutionNm));
    }
}
