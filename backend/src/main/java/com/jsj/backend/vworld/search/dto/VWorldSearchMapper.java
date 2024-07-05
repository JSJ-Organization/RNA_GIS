package com.jsj.backend.vworld.search.dto;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * VWorld 검색 API 응답을 VWorldSearchResponse로 매핑하는 클래스.
 */
@Service
public class VWorldSearchMapper {

    /**
     * VWorldSearchApiResponse를 VWorldSearchResponse 리스트로 변환합니다.
     * 응답이 null이거나 결과가 없는 경우 빈 리스트를 반환합니다.
     *
     * @param apiResponse VWorld 검색 API 응답 객체
     * @return VWorldSearchResponse 리스트
     */
    public List<VWorldSearchResponse> apiToResponse(VWorldSearchApiResponse apiResponse) {
        if (apiResponse == null
                || apiResponse.getResponse() == null
                || apiResponse.getResponse().getResult() == null) {
            return emptyList();
        }

        List<VWorldSearchApiResponse.Response.Result.Item> items = apiResponse.getResponse().getResult().getItems();
        if (items == null || items.isEmpty()) {
            return emptyList();
        }

        return items.stream().map(item -> VWorldSearchResponse.builder()
                .x(Double.parseDouble(item.getPoint().getX()))
                .y(Double.parseDouble(item.getPoint().getY()))
                .roadNameAddress(item.getAddress().getRoad())
                .parcelAddress(item.getAddress().getParcel())
                .zipcode(item.getAddress().getZipcode())
                .bldnm(item.getAddress().getBldnm())
                .bldnmdc(item.getAddress().getBldnmdc())
                .build()
        ).collect(toList());
    }
}
