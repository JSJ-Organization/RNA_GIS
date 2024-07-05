package com.jsj.backend.frcnRentInfo.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FrcnRentInfoResponse {

    private double x;
    private double y;

    private String roadNameAddress;
    private String parcelAddress;

    /** 사업소명 */
    private String officeNm;
    /** 사업소 전화번호 */
    private String officePhoneNumber;

    /** 트랙터 및 작업기 보유 대수 */
    private String trctorHoldCo;
    /** 경운기 및 작업기 보유 대수 */
    private String cultvtHoldCo;
    /** 관리기 및 작업기 보유 대수 */
    private String manageHoldCo;
    /** 땅속 작물 수확기 보유 대수 */
    private String harvestHoldCo;
    /** 탈곡기 및 정선 작업기 보유 대수 */
    private String thresherHoldCo;
    /** 자주형 파종기 보유 대수 */
    private String planterHoldCo;
    /** 이앙 작업기 보유 대수 */
    private String transplantHoldCo;
    /** 벼 수확 및 운반 작업기 보유 대수 */
    private String rcepntHoldCo;
    /** 기타 임대 농기계 보유 정보 */
    private String etcRentHoldCo;

    /** 관리 기관 전화번호 */
    private String phoneNumber;
    /** 관리 기관명 */
    private String institutionNm;
    /** 데이터 기준 일자 */
    private String referenceDate;
    /** 제공 기관 코드 */
    private String instt_code;
}