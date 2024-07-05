package com.jsj.backend.vworld.search.dto;

import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service
public class VWorldSearchMapper {

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
