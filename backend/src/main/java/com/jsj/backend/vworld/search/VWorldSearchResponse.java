package com.jsj.backend.vworld.search;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class VWorldSearchResponse {
    private double x;
    private double y;
    private String roadNameAddress;
    private String parcelAddress;
    /** 우편번호 */
    private Integer zipcode;
    /** 건물명 */
    private String bldnm;
    /** 건물 상세명 */
    private String bldnmdc;
}
