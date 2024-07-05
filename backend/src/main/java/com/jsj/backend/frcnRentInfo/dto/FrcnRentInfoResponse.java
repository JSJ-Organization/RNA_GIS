package com.jsj.backend.frcnRentInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "전국 농기계 임대 정보 응답 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FrcnRentInfoResponse {
    @Schema(description = "식별자")
    private Long id;
    /** X 좌표 */
    @Schema(description = "X 좌표")
    private double x;
    /** Y 좌표 */
    @Schema(description = "Y 좌표")
    private double y;
    /** 도로명 주소 */
    @Schema(description = "도로명 주소")
    private String roadNameAddress;
    /** 지번 주소 */
    @Schema(description = "지번 주소")
    private String parcelAddress;
    /** 사업소명 */
    @Schema(description = "사업소명")
    private String officeNm;
    /** 사업소 전화번호 */
    @Schema(description = "사업소 전화번호")
    private String officePhoneNumber;
    /** 트랙터 및 작업기 보유 대수 */
    @Schema(description = "트랙터 및 작업기 보유 대수")
    private String trctorHoldCo;
    /** 경운기 및 작업기 보유 대수 */
    @Schema(description = "경운기 및 작업기 보유 대수")
    private String cultvtHoldCo;
    /** 관리기 및 작업기 보유 대수 */
    @Schema(description = "관리기 및 작업기 보유 대수")
    private String manageHoldCo;
    /** 땅속 작물 수확기 보유 대수 */
    @Schema(description = "땅속 작물 수확기 보유 대수")
    private String harvestHoldCo;
    /** 탈곡기 및 정선 작업기 보유 대수 */
    @Schema(description = "탈곡기 및 정선 작업기 보유 대수")
    private String thresherHoldCo;
    /** 자주형 파종기 보유 대수 */
    @Schema(description = "자주형 파종기 보유 대수")
    private String planterHoldCo;
    /** 이앙 작업기 보유 대수 */
    @Schema(description = "이앙 작업기 보유 대수")
    private String transplantHoldCo;
    /** 벼 수확 및 운반 작업기 보유 대수 */
    @Schema(description = "벼 수확 및 운반 작업기 보유 대수")
    private String rcepntHoldCo;
    /** 기타 임대 농기계 보유 정보 */
    @Schema(description = "기타 임대 농기계 보유 정보")
    private String etcRentHoldCo;
    /** 관리 기관 전화번호 */
    @Schema(description = "관리 기관 전화번호")
    private String phoneNumber;
    /** 관리 기관명 */
    @Schema(description = "관리 기관명")
    private String institutionNm;
    /** 데이터 기준 일자 */
    @Schema(description = "데이터 기준 일자")
    private String referenceDate;
    /** 제공 기관 코드 */
    @Schema(description = "제공 기관 코드")
    private String instt_code;
}
