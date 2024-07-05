package com.jsj.backend.batch.frcnRentInfo;

import com.jsj.backend.frcnRentInfo.entity.FrcnRentInfoRepository;
import com.jsj.backend.util.DateUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FrcnRentInfoJobExecutionListener implements JobExecutionListener {

    private final FrcnRentInfoRepository entityRepository;
    private final static String IMPORT_FRCN_RENT_INFO_JOB = "importFrcnRentInfo";
    @Override
    public void beforeJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        switch (jobName) {
            case IMPORT_FRCN_RENT_INFO_JOB: {
                String today = DateUtil.getTodayDateString();
                boolean existsByBatchDate = entityRepository.existsByBatchDate(today);
                if(existsByBatchDate) {
                    log.info("delete FRCN_RENT_INFO already exited data:: base date: {}", today);
                    entityRepository.deleteAllByBatchDate(today);
                }
                 break;
            }
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        switch (jobName) {
            case IMPORT_FRCN_RENT_INFO_JOB: {
                String today = DateUtil.getTodayDateString();
                boolean existsByBatchDate = entityRepository.existsByBatchDate(today);
                if(!existsByBatchDate) {
                    log.info("does not exited data :: base date: {}", today);
                    break;
                }
                boolean existsPastDataByBatchDate = entityRepository.existsPastDataByBatchDate(today);
                if(existsPastDataByBatchDate) {
                    log.info("delete FRCN_RENT_INFO, past data :: base date: {}", today);
                    entityRepository.deletePastDataByDate(today);
                }
                break;
            }
        }
    }
}
