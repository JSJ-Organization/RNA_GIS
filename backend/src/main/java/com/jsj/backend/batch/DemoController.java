package com.jsj.backend.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    private final JobLauncher jobLauncher;
    private final Job frcnRentInfoJob;

    public DemoController(
            JobLauncher jobLauncher,
            @Qualifier("frcnRentInfoJob") Job frcnRentInfoJob) {
        this.jobLauncher = jobLauncher;
        this.frcnRentInfoJob = frcnRentInfoJob;
    }

    @GetMapping("/batch-test")
    public String test() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            JobExecution jobExecution = jobLauncher.run(frcnRentInfoJob, jobParameters);
            return "Job ID: " + jobExecution.getId() + " 상태: " + jobExecution.getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "작업 실행 중 오류가 발생했습니다.";
        }
    }
}