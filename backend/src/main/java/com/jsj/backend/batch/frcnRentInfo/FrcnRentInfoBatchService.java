package com.jsj.backend.batch.frcnRentInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FrcnRentInfoBatchService {
    private final JobLauncher jobLauncher;
    private final Job frcnRentInfoJob;

    public FrcnRentInfoBatchService(
            JobLauncher jobLauncher,
            @Qualifier("frcnRentInfoJob") Job frcnRentInfoJob) {
        this.jobLauncher = jobLauncher;
        this.frcnRentInfoJob = frcnRentInfoJob;
    }

    public String frcnRentInfoJob() {
        log.info("Call batch job: {}", frcnRentInfoJob.getName());
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(frcnRentInfoJob, jobParameters);
            return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
        } catch (Exception e) {
            log.error("Error during job execution", e);
            return "작업 실행 중 오류가 발생했습니다.";
        }
    }
}
