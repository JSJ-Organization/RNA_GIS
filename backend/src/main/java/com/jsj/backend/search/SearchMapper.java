package com.jsj.backend.search;

import com.jsj.backend.frcnRentInfo.FrcnRentInfo;
import com.jsj.backend.frcnRentInfo.FrcnRentInfoMapper;
import com.jsj.backend.vworld.search.VWorldSearchApiResponse;
import com.jsj.backend.vworld.search.VWorldSearchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SearchMapper {
    private final FrcnRentInfoMapper frcnRentInfoMapper;
    private final VWorldSearchMapper vWorldSearchMapper;

    private static final String FRCN_RENT_INFO_API_CRS = "EPSG:4326";

    public SearchResponse vWorldSearchToSearch(
            String requestQuery,
            VWorldSearchApiResponse apiResponse
    ){

        return SearchResponse.builder()
                .totalData(apiResponse.getResponse().getRecord().getTotal())
                .currentPage(apiResponse.getResponse().getPage().getCurrent())
                .totalPage(apiResponse.getResponse().getPage().getTotal())
                .requestQuery(requestQuery)
                .crs(apiResponse.getResponse().getResult().getCrs())
                .vWorldSearchResponses(Optional.ofNullable(vWorldSearchMapper.apiToResponse(apiResponse)))
                .build();
    }

    public SearchResponse frcnRentInfoToSearch(
            List<FrcnRentInfo> entities,
            String requestQuery,
            Integer currentPage
    ) {

        return SearchResponse.builder()
                .currentPage(currentPage)
                .totalData(entities.size())
                .requestQuery(requestQuery)
                .crs(FRCN_RENT_INFO_API_CRS)
                .frcnRentInfoResponses(Optional.ofNullable(frcnRentInfoMapper.entityToResponse(entities)))
                .build();
    }

}