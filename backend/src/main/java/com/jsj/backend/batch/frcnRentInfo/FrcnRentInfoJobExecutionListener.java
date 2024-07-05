package com.jsj.backend.batch.frcnRentInfo;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfoRepository;
import com.jsj.backend.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * 농기계 임대 정보 배치 작업의 실행 리스너 클래스.
 * 배치 작업의 전후에 필요한 추가 작업을 처리합니다.
 */
@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FrcnRentInfoJobExecutionListener implements JobExecutionListener {

    private final FrcnRentInfoRepository entityRepository; // 농기계 임대 정보 저장소
    private static final String IMPORT_FRCN_RENT_INFO_JOB = "importFrcnRentInfo"; // 배치 작업 이름

    /**
     * 배치 작업 시작 전에 호출되는 메서드.
     * 오늘 날짜의 데이터를 삭제합니다.
     *
     * @param jobExecution 현재 실행 중인 JobExecution 객체
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        if (IMPORT_FRCN_RENT_INFO_JOB.equals(jobName)) {
            String today = DateUtil.getTodayDateString();
            boolean existsByBatchDate = entityRepository.existsByBatchDate(today);
            if (existsByBatchDate) {
                log.info("delete FRCN_RENT_INFO already existed data:: base date: {}", today);
                entityRepository.deleteAllByBatchDate(today);
            }
        }
    }

    /**
     * 배치 작업 완료 후 호출되는 메서드.
     * 오늘 날짜의 이전 데이터를 삭제합니다.
     *
     * @param jobExecution 현재 실행 중인 JobExecution 객체
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        if (IMPORT_FRCN_RENT_INFO_JOB.equals(jobName)) {
            String today = DateUtil.getTodayDateString();
            boolean existsByBatchDate = entityRepository.existsByBatchDate(today);
            if (!existsByBatchDate) {
                log.info("does not exist data :: base date: {}", today);
                return;
            }
            boolean existsPastDataByBatchDate = entityRepository.existsPastDataByBatchDate(today);
            if (existsPastDataByBatchDate) {
                log.info("delete FRCN_RENT_INFO, past data :: base date: {}", today);
                entityRepository.deletePastDataByDate(today);
            }
        }
    }
}
