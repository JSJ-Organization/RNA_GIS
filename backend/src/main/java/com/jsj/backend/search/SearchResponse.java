package com.jsj.backend.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jsj.backend.search.frcnRentInfo.FrcnRentInfoResponse;
import com.jsj.backend.search.vworld.VWorldSearchApiResponse;
import com.jsj.backend.search.vworld.VWorldSearchResponse;
import lombok.*;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SearchResponse {
    /** 데이터 총 개수 */
    private Integer totalData;
    /** 현재 페이지 */
    private Integer currentPage;
    /** 전체 페이지 수 */
    private Integer totalPage;
    /** 사용자가 요청한 값 */
    private String requestQuery;
    /** 좌표계 */
    private String crs;
    /**  */
    @Builder.Default
    private Optional<List<VWorldSearchResponse>> vWorldSearchResponses = Optional.empty();
    /**  */
    @Builder.Default
    private Optional<List<FrcnRentInfoResponse>> frcnRentInfoResponses = Optional.empty();
}
