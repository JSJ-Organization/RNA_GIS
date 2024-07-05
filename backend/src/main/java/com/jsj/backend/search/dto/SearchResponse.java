package com.jsj.backend.search.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jsj.backend.frcnRentInfo.dto.FrcnRentInfoResponse;
import com.jsj.backend.vworld.search.dto.VWorldSearchResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.Optional;

@Schema(description = "검색 정보 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchResponse {
    /** 데이터 총 개수 */
    @Schema(description = "데이터 총 개수")
    private Integer totalData;
    /** 현재 페이지 */
    @Schema(description = "현재 페이지")
    private Integer currentPage;
    /** 전체 페이지 수 */
    @Schema(description = "전체 페이지 수")
    private Integer totalPage;
    /** 사용자가 요청한 값 */
    @Schema(description = "사용자가 요청한 값")
    private String requestQuery;
    /** 좌표계 */
    @Schema(description = "좌표계")
    private String crs;
    /** VWorld 검색 응답 목록 */
    @Schema(description = "VWorld 검색 응답 목록")
    @Builder.Default
    private Optional<List<VWorldSearchResponse>> vWorldSearchResponses = Optional.empty();
    /** 농기계 임대 정보 응답 목록 */
    @Schema(description = "농기계 임대 정보 응답 목록")
    @Builder.Default
    private Optional<List<FrcnRentInfoResponse>> frcnRentInfoResponses = Optional.empty();
}
